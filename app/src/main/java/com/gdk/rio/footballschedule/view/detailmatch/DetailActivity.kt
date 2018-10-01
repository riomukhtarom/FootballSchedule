package com.gdk.rio.footballschedule.view.detailmatch

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.gdk.rio.footballschedule.R
import com.gdk.rio.footballschedule.R.drawable.ic_add_to_favorites
import com.gdk.rio.footballschedule.R.drawable.ic_added_to_favorites
import com.gdk.rio.footballschedule.R.id.add_to_favorite
import com.gdk.rio.footballschedule.R.menu.detail_menu
import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.database.FavoriteMatch
import com.gdk.rio.footballschedule.database.database
import com.gdk.rio.footballschedule.model.match.Match
import com.gdk.rio.footballschedule.model.team.Team
import com.gdk.rio.footballschedule.presenter.DetailPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var detailPresenter: DetailPresenter
    private lateinit var match: Match
    private lateinit var matchId: String
    private lateinit var homeId: String
    private lateinit var awayId: String
    val api = ApiRepository()
    val gson = Gson()
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        match = Match()

        matchId = intent.getStringExtra("matchId")
        homeId = intent.getStringExtra("homeId")
        awayId = intent.getStringExtra("awayId")

        favoriteState()

        detailPresenter = DetailPresenter(this, api, gson)
        detailPresenter.getMatchDetail(matchId, homeId, awayId)
    }

    override fun showLoading() {
        pb_detail.visibility = ProgressBar.VISIBLE
    }

    override fun hideLoading() {
        pb_detail.visibility = ProgressBar.INVISIBLE
    }

    override fun showDetail(events: List<Match>, homeTeam: List<Team>, awayTeam: List<Team>) {
        match.matchId = events[0].matchId
        match.matchDate = events[0].matchDate
        match.homeId = events[0].homeId
        match.homeTeam = events[0].homeTeam
        match.homeScore = events[0].homeScore
        match.awayId = events[0].awayId
        match.awayTeam = events[0].awayTeam
        match.awayScore = events[0].awayScore

        tv_dateDetail.text = events[0].matchDate

        //home
        Picasso.get().load(homeTeam[0].teamLogo).into(iv_homeLogo)
        tv_homeName.text = events[0].homeTeam
        tv_homeScore.text = events[0].homeScore
        tv_homeFormation.text = events[0].homeFormation
        tv_homeGoals.text = events[0].homeGoalDetails
        tv_homeShots.text = events[0].homeShots
        tv_homeGoalKeeper.text = events[0].homeGoalKeeper
        tv_homeDefense.text = events[0].homeDefense
        tv_homeMidfield.text = events[0].homeMidfield
        tv_homeForward.text = events[0].homeForward

        //away
        Picasso.get().load(awayTeam[0].teamLogo).into(iv_awayLogo)
        tv_awayName.text = events[0].awayTeam
        tv_awayScore.text = events[0].awayScore
        tv_awayFormation.text = events[0].awayFormation
        tv_awayGoals.text = events[0].awayGoalDetails
        tv_awayShots.text = events[0].awayShots
        tv_awayGoalKeeper.text = events[0].awayGoalKeeper
        tv_awayDefense.text = events[0].awayDefense
        tv_awayMidfield.text = events[0].awayMidfield
        tv_awayForward.text = events[0].awayForward
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }

            add_to_favorite -> {
                if (isFavorite)
                    removeFromFavoriteMatch()
                else
                    addToFavoriteMatch()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite(){
        if (isFavorite){
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        }else{
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
        }
    }

    private fun removeFromFavoriteMatch(){
        try {
            database.use {
                delete(FavoriteMatch.TABLE_FAVORITE_MATCH, "(MATCH_ID = {id})",
                        "id" to matchId)
            }
            snackbar(sv_detail, "Removed from favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(sv_detail, e.localizedMessage).show()
        }
    }

    private fun addToFavoriteMatch(){
        try {
            database.use {
                insert(FavoriteMatch.TABLE_FAVORITE_MATCH,
                        FavoriteMatch.MATCH_ID to match.matchId.toString(),
                        FavoriteMatch.MATCH_DATE to match.matchDate.toString(),

                        //HOME
                        FavoriteMatch.HOME_ID to match.homeId.toString(),
                        FavoriteMatch.HOME_TEAM to match.homeTeam.toString(),
                        FavoriteMatch.HOME_SCORE to match.homeScore.toString(),

                        //AWAY
                        FavoriteMatch.AWAY_ID to match.awayId.toString(),
                        FavoriteMatch.AWAY_TEAM to match.awayTeam.toString(),
                        FavoriteMatch.AWAY_SCORE to match.awayScore.toString()
                        )
            }
            snackbar(sv_detail, "Add to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(sv_detail, e.localizedMessage).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                    .whereArgs("(MATCH_ID = {id})",
                            "id" to matchId)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

}

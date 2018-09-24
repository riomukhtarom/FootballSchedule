package com.gdk.rio.footballschedule.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.gdk.rio.footballschedule.R
import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.model.match.Match
import com.gdk.rio.footballschedule.model.team.Team
import com.gdk.rio.footballschedule.presenter.DetailPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var detailPresenter: DetailPresenter
    private lateinit var events: Match
    val api = ApiRepository()
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        events = intent.getParcelableExtra<Match>("events")

        detailPresenter = DetailPresenter(this, api, gson)
        detailPresenter.getTeamLogo(events.homeId, events.awayId)
    }

    override fun showLoading() {
        pb_detail.visibility = ProgressBar.VISIBLE
    }

    override fun hideLoading() {
        pb_detail.visibility = ProgressBar.INVISIBLE
    }

    override fun showDetail(homeTeamLogo: List<Team>, awayTeamLogo: List<Team>) {

        tv_dateDetail.text = events.matchDate
        homeTeamLogo[0].teamLogo

        //home
        Picasso.get().load(homeTeamLogo[0].teamLogo).into(iv_homeLogo)
        tv_homeName.text = events.homeTeam
        tv_homeScore.text = events.homeScore
        tv_homeFormation.text = events.homeFormation
        tv_homeGoals.text = events.homeGoalDetails
        tv_homeShots.text = events.homeShots
        tv_homeGoalKeeper.text = events.homeGoalKeeper
        tv_homeDefense.text = events.homeDefense
        tv_homeMidfield.text = events.homeMidfield
        tv_homeForward.text = events.homeForward

        //away
        Picasso.get().load(awayTeamLogo[0].teamLogo).into(iv_awayLogo)
        tv_awayName.text = events.awayTeam
        tv_awayScore.text = events.awayScore
        tv_awayFormation.text = events.awayFormation
        tv_awayGoals.text = events.awayGoalDetails
        tv_awayShots.text = events.awayShots
        tv_awayGoalKeeper.text = events.awayGoalKeeper
        tv_awayDefense.text = events.awayDefense
        tv_awayMidfield.text = events.awayMidfield
        tv_awayForward.text = events.awayForward
    }

}

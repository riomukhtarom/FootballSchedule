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

        tv_dateDetail.setText(events.matchDate)
        homeTeamLogo[0].teamLogo

        //home
        Picasso.get().load(homeTeamLogo[0].teamLogo).into(iv_homeLogo)
        tv_homeName.setText(events.homeTeam)
        tv_homeScore.setText(events.homeScore)
        tv_homeFormation.setText(events.homeFormation)
        tv_homeGoals.setText(events.homeGoalDetails)
        tv_homeShots.setText(events.homeShots)
        tv_homeGoalKeeper.setText(events.homeGoalKeeper)
        tv_homeDefense.setText(events.homeDefense)
        tv_homeMidfield.setText(events.homeMidfield)
        tv_homeForward.setText(events.homeForward)

        //away
        Picasso.get().load(awayTeamLogo[0].teamLogo).into(iv_awayLogo)
        tv_awayName.setText(events.awayTeam)
        tv_awayScore.setText(events.awayScore)
        tv_awayFormation.setText(events.awayFormation)
        tv_awayGoals.setText(events.awayGoalDetails)
        tv_awayShots.setText(events.awayShots)
        tv_awayGoalKeeper.setText(events.awayGoalKeeper)
        tv_awayDefense.setText(events.awayDefense)
        tv_awayMidfield.setText(events.awayMidfield)
        tv_awayForward.setText(events.awayForward)
    }

}

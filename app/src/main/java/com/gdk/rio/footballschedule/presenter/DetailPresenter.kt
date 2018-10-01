package com.gdk.rio.footballschedule.presenter

import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.api.TheSportDBApi
import com.gdk.rio.footballschedule.model.match.MatchResponse
import com.gdk.rio.footballschedule.model.team.TeamResponse
import com.gdk.rio.footballschedule.view.detailmatch.DetailView
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(
        private val view: DetailView,
        private val apiRepository: ApiRepository,
        private val gson: Gson){

    fun getMatchDetail(matchId: String?, homeTeamId: String?, awayTeamId: String?){
        view.showLoading()
        doAsync {

            val eventDetail = gson.fromJson(
                    apiRepository.request(TheSportDBApi.getEventDetails(matchId)),
                    MatchResponse::class.java
            )

            val homeTeamLogo = gson.fromJson(
                    apiRepository.request(TheSportDBApi.getTeam(homeTeamId)),
                    TeamResponse::class.java
            )

            val awayTeamLogo = gson.fromJson(
                    apiRepository.request(TheSportDBApi.getTeam(awayTeamId)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showDetail(eventDetail.events, homeTeamLogo.teams, awayTeamLogo.teams)
            }
        }
    }
}
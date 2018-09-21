package com.gdk.rio.footballschedule.presenter

import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.api.TheSportDBApi
import com.gdk.rio.footballschedule.model.team.TeamResponse
import com.gdk.rio.footballschedule.view.DetailView
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(
        private val view: DetailView,
        private val apiRepository: ApiRepository,
        private val gson: Gson){

    fun getTeamLogo(homeTeamId: String?, awayTeamId: String?){
        view.showLoading()
        doAsync {
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
                view.showDetail(homeTeamLogo.teams, awayTeamLogo.teams)
            }
        }
    }
}
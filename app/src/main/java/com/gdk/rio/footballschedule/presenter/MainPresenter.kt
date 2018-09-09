package com.gdk.rio.footballschedule.presenter

import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.api.TheSportDBApi
import com.gdk.rio.footballschedule.model.MatchResponse
import com.gdk.rio.footballschedule.view.MainView
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter (private val view: MainView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson)  {

    fun getMatchList(event: String, leagueId: String){
        view.showLoading()
        doAsync {
            val eventData = gson.fromJson(
                    apiRepository.request(TheSportDBApi.getEvent(event, leagueId)),
                    MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
//                view.showMatch(eventData.matchs)
            }
        }
    }
}
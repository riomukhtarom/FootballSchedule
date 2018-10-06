package com.gdk.rio.footballschedule.presenter

import com.gdk.rio.footballschedule.CoroutineContextProvider
import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.api.TheSportDBApi
import com.gdk.rio.footballschedule.model.match.MatchResponse
import com.gdk.rio.footballschedule.view.main.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ResultMatchPresenter (private val view: MatchView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider())  {

    fun getMatchList(event: String, leagueId: String){
        view.showLoading()
        /*doAsync {
            val eventData = gson.fromJson(
                    apiRepository.request(TheSportDBApi.getEvent(event, leagueId)),
                    MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatch(eventData.events)
            }
        }*/

        async(context.main){
            val eventData = bg {
                gson.fromJson(
                        apiRepository.request(TheSportDBApi.getEvent(event, leagueId)),
                        MatchResponse::class.java
                )
            }
            view.hideLoading()
            view.showMatch(eventData.await().events)
        }
    }
}
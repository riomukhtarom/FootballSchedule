package com.gdk.rio.footballschedule.presenter

import com.gdk.rio.footballschedule.TestContextProvider
import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.api.TheSportDBApi
import com.gdk.rio.footballschedule.model.match.Match
import com.gdk.rio.footballschedule.model.match.MatchResponse
import com.gdk.rio.footballschedule.view.main.MatchView
import com.google.gson.Gson
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ResultMatchPresenterTest {
    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson


    private lateinit var resultMatchPresenter: ResultMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        resultMatchPresenter = ResultMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchList() {
        val matchs: MutableList<Match> = mutableListOf()
        val matchResponse = MatchResponse(matchs)
        val event = "eventspastleague.php"
        val leagueId = "4328"

        `when`(gson.fromJson(
                apiRepository.request(TheSportDBApi.getEvent(event, leagueId)),
                MatchResponse::class.java
        )).thenReturn(matchResponse)

        resultMatchPresenter.getMatchList(event, leagueId)

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showMatch(matchs)

    }
}
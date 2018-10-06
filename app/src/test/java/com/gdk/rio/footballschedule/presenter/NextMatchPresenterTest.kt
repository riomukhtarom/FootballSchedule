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

class NextMatchPresenterTest {

    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson


    private lateinit var nextMatchPresenter: NextMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        nextMatchPresenter = NextMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchList() {
        val matchs: MutableList<Match> = mutableListOf()
        val matchResponse = MatchResponse(matchs)
        val event = "eventsnextleague.php"
        val leagueId = "4328"

        `when`(gson.fromJson(
                apiRepository.request(TheSportDBApi.getEvent(event, leagueId)),
                MatchResponse::class.java
        )).thenReturn(matchResponse)

        nextMatchPresenter.getMatchList(event, leagueId)

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showMatch(matchs)
    }
}
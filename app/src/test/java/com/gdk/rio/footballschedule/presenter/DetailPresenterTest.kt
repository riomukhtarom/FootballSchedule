package com.gdk.rio.footballschedule.presenter

import com.gdk.rio.footballschedule.TestContextProvider
import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.api.TheSportDBApi
import com.gdk.rio.footballschedule.model.match.Match
import com.gdk.rio.footballschedule.model.match.MatchResponse
import com.gdk.rio.footballschedule.model.team.Team
import com.gdk.rio.footballschedule.model.team.TeamResponse
import com.gdk.rio.footballschedule.view.detailmatch.DetailView
import com.google.gson.Gson
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DetailPresenterTest {

    @Mock
    private lateinit var view: DetailView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson


    private lateinit var detailPresenter: DetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        detailPresenter = DetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchDetail() {
        val event: MutableList<Match> = mutableListOf()
        val homeTeam: MutableList<Team> = mutableListOf()
        val awayTeam: MutableList<Team> = mutableListOf()
        val eventResponse = MatchResponse(event)
        val homeTeamResponse = TeamResponse(homeTeam)
        val awayTeamResponse = TeamResponse(awayTeam)
        val matchId = "441613"
        val homeId = "133604"
        val awayId = "133605 "

        `when`(gson.fromJson(
                apiRepository.request(TheSportDBApi.getEventDetails(matchId)),
                MatchResponse::class.java
        )).thenReturn(eventResponse)

        `when`(gson.fromJson(
                apiRepository.request(TheSportDBApi.getTeam(homeId)),
                TeamResponse::class.java
        )).thenReturn(homeTeamResponse)

        `when`(gson.fromJson(
                apiRepository.request(TheSportDBApi.getTeam(awayId)),
                TeamResponse::class.java
        )).thenReturn(awayTeamResponse)

        detailPresenter.getMatchDetail(matchId, homeId, awayId)

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showDetail(event, homeTeam, awayTeam)
    }
}
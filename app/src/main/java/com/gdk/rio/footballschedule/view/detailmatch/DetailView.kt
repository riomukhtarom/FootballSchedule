package com.gdk.rio.footballschedule.view.detailmatch

import com.gdk.rio.footballschedule.model.match.Match
import com.gdk.rio.footballschedule.model.team.Team

interface  DetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(events: List<Match>, homeTeam: List<Team>, awayTeam: List<Team>)
}
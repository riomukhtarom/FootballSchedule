package com.gdk.rio.footballschedule.view

import com.gdk.rio.footballschedule.model.team.Team

interface  DetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(homeTeam: List<Team>, awayTeam: List<Team>)
}
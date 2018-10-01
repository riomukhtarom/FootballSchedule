package com.gdk.rio.footballschedule.view.main

import com.gdk.rio.footballschedule.model.match.Match

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatch(matchList: List<Match>)
}
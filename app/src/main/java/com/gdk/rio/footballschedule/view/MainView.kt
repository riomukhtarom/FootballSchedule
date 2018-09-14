package com.gdk.rio.footballschedule.view

import com.gdk.rio.footballschedule.model.Match

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showMatch(matchList: List<Match>)
}
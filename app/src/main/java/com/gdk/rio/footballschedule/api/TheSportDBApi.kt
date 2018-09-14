package com.gdk.rio.footballschedule.api

import android.net.Uri
import com.gdk.rio.footballschedule.BuildConfig

object TheSportDBApi {
    fun getEvent(event: String?, leagueId: String?): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.API_KEY)
                .appendPath(event)
                .appendQueryParameter("id", leagueId)
                .toString()
    }
}
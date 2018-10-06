package com.gdk.rio.footballschedule.api

import com.gdk.rio.footballschedule.BuildConfig

object TheSportDBApi {
    fun getEvent(event: String?, leagueId: String?): String{
        return "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.API_KEY}/${event}?id=${leagueId}"
        /*return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.API_KEY)
                .appendPath(event)
                .appendQueryParameter("id", leagueId)
                .toString()*/
    }

    fun getEventDetails(eventId: String?): String{
        return "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.API_KEY}/lookupevent.php?id=${eventId}"
        /*return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.API_KEY)
                .appendPath("lookupevent.php")
                .appendQueryParameter("id", eventId)
                .toString()*/
    }

    fun getTeam(teamId: String?): String{
        return "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.API_KEY}/lookupteam.php?id=${teamId}"
        /*return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.API_KEY)
                .appendPath("lookupteam.php")
                .appendQueryParameter("id", teamId)
                .toString()*/
    }
}
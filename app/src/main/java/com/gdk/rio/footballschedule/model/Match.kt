package com.gdk.rio.footballschedule.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Match (
        @SerializedName("dateEvent")
        var matchDate: String? = null,

        @SerializedName("strHomeTeam")
        var teamHome: String? = null,

        @SerializedName("strAwayTeam")
        var teamAway: String? = null,

        @SerializedName("intHomeScore")
        var scoreHome: String? = null,

        @SerializedName("intAwayScore")
        var scoreAway: String? = null
)
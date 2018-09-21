package com.gdk.rio.footballschedule.model.team

import com.google.gson.annotations.SerializedName

data class Team (
        @SerializedName("strTeamBadge")
        var teamLogo: String? = null
)
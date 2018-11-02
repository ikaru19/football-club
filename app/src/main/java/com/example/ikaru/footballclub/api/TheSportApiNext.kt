package com.example.ikaru.footballclub.api

import android.net.Uri
import com.example.ikaru.footballclub.BuildConfig

object TheSportApiNext {
    fun getTeams(id: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("eventsnextleague.php")
            .appendQueryParameter("id", id)
            .build()
            .toString()
    }
}
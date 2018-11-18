package com.example.ikaru.footballclub.api

import android.net.Uri
import com.example.ikaru.footballclub.BuildConfig

class TheSportDBApi(val id: String?){
    private fun urlBuild(path: String?, id: String?) = BuildConfig.BASE_URL+"api/v1/json/"+BuildConfig.TSDB_API_KEY+"/"+path+"?id="+id

    fun getprevsechdule() = urlBuild("eventspastleague.php",id)
    fun getnextsechdule() = urlBuild("eventsnextleague.php",id)
    fun getmatchDetail() = urlBuild("lookupevent.php",id)
    fun getTeamDetail() = urlBuild("lookupteam.php",id)
}

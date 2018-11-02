package com.example.ikaru.footballclub.EventDetail


import com.example.ikaru.footballclub.api.ApiRes
import com.example.ikaru.footballclub.api.ApiRespository
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class EventDetailPresenter (private val view: EventDetail,
                            private val apiEvent: String,
                            private val apiHome : String,
                            private val apiAway : String,
                            private val gson: Gson){
    fun getListDetail() {
        view.showLoading()

        doAsync {
            val eventDetail = gson.fromJson(ApiRespository().doRequest(apiEvent), ApiRes::class.java)
             val homeDetail = gson.fromJson(ApiRespository().doRequest(apiHome), ApiRes::class.java)
            val awayDetail = gson.fromJson(ApiRespository().doRequest(apiAway), ApiRes::class.java)

            uiThread {
                view.hideLoading()
                view.showAll(eventDetail.events[0],homeDetail.teams[0],awayDetail.teams[0])
            }
        }
    }

}
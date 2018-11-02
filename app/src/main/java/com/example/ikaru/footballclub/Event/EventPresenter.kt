package com.example.ikaru.footballclub.Event

import com.example.ikaru.footballclub.api.ApiRes
import com.example.ikaru.footballclub.api.ApiRespository
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventPresenter (private val view: EventFragment, private val api: String, private val gson: Gson) {

    fun getList() {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(ApiRespository().doRequest(api), ApiRes::class.java)

            uiThread {
                view.hideLoading()
                view.showList(data.events)
            }
        }
    }
}

package com.example.ikaru.footballclub.Event

import com.example.ikaru.footballclub.api.ApiRes
import com.example.ikaru.footballclub.api.ApiRespository
import com.example.ikaru.footballclub.api.TheSportDBApi
import com.example.ikaru.footballclub.model.MatchResponse
import com.example.ikaru.footballclub.util.CoroutineContextProvider
import com.google.gson.Gson
import org.jetbrains.anko.coroutines.experimental.bg
import kotlinx.coroutines.experimental.async

class EventPresenter (private val view: EventView,
                      private val apiRepository: ApiRespository,
                      private val gson: Gson,
                      private val fixture: Int = 1,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()

) {

    fun getList() {
        view.showLoading()
        val sportDB = TheSportDBApi("4328")
        val api = if (fixture == 1) sportDB.getprevsechdule() else sportDB.getnextsechdule()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(api), MatchResponse::class.java)
//                gson.fromJson(apiRepository.doRequest(api), ApiRes::class.java)
            }

            view.showList(data.await().events)
            view.hideLoading()
        }
    }
}

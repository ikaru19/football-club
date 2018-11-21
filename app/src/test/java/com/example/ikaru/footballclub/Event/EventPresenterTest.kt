package com.example.ikaru.footballclub.Event

import com.example.ikaru.footballclub.api.ApiRespository
import com.example.ikaru.footballclub.api.TheSportDBApi
import com.example.ikaru.footballclub.api.TheSportDBApiMatch
import com.example.ikaru.footballclub.model.MatchResponse
import com.example.ikaru.footballclub.model.Match
import com.example.ikaru.footballclub.util.TestContextProvider
import com.google.gson.Gson
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventPresenterTest {

    @Mock
    private
    lateinit var view: EventView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRespository

    private lateinit var presenter: EventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = EventPresenter(
            view,
            apiRepository,
            gson,
            1,
            TestContextProvider()
        )
    }

    @Test
    fun getList() {
        val matches: MutableList<Match> = mutableListOf()
        val leagueId = "4328"
        val response = MatchResponse(matches)
        val db = TheSportDBApi(leagueId)

        Mockito.`when`(gson.fromJson(apiRepository.doRequest(db.getprevsechdule()),
            MatchResponse::class.java
        )).thenReturn(response)

        presenter.getList()

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showList(response.events)
        Mockito.verify(view).hideLoading()
    }
}
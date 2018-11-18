package com.example.ikaru.footballclub.api

import android.net.Uri
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

class TheSportDBApiMatchTest {

    @Test
    fun getTeams() {
        val theSportDBApi = TheSportDBApi("4328")
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        assertEquals(url , theSportDBApi.getprevsechdule())
    }
}
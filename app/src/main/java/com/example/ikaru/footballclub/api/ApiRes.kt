package com.example.ikaru.footballclub.api

import com.example.ikaru.footballclub.model.Match

data class ApiRes (    val events : List<Match>, val teams: List<Match>)

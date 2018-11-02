package com.example.ikaru.footballclub.Event

import com.example.ikaru.footballclub.model.Match

interface EventView {
    fun showLoading()
    fun hideLoading()
    fun showList(data : List<Match>)
}
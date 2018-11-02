package com.example.ikaru.footballclub.EventDetail

import com.example.ikaru.footballclub.model.Match
import com.example.ikaru.footballclub.model.Team
interface EventDetailView {
        fun showLoading()
        fun hideLoading()
        fun showAll(match: Match,home : Match , away : Match)

}
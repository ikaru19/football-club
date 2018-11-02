package com.example.ikaru.footballclub.EventDetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.ikaru.footballclub.model.Match
import com.example.ikaru.footballclub.R
import com.example.ikaru.footballclub.R.color.colorAccent
import com.example.ikaru.footballclub.api.SportApiMatch
import com.example.ikaru.footballclub.api.TheSportDBApi
import com.example.ikaru.footballclub.api.TheSportDBApiMatch
import com.example.ikaru.footballclub.model.Team
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.support.v4.onRefresh

class EventDetail : AppCompatActivity() , EventDetailView{
    private lateinit var event: Match


    private lateinit var presenter: EventDetailPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        event = intent.getParcelableExtra("Event")

        someDate.text = event.dateEvent
        tvTeam1Score.text = event.intHomeScore
        tvTeam1Name.text = event.strHomeTeam
        tvTeam2Score.text = event.intAwayScore
        tvTeam2Name.text = event.strAwayTeam


        val apiMatchDet = TheSportDBApi(event.idEvent).getmatchDetail()
        val apiHomeTeam = TheSportDBApi(event.idHomeTeam).getTeamDetail()
        val apiAwayTeam = TheSportDBApi(event.idAwayTeam).getTeamDetail()
        val gson = Gson()
        presenter = EventDetailPresenter(this,apiMatchDet,apiHomeTeam,apiAwayTeam,gson)
        presenter.getListDetail()

        detailSwipe.onRefresh {
            presenter.getListDetail()
        }
        detailSwipe.setColorSchemeResources(colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)
    }

    override fun showLoading() {
        detailSwipe.isRefreshing = true
    }

    override fun hideLoading() {
        detailSwipe.isRefreshing = false
    }

    override fun showAll(match: Match, homeTeam : Match, awayTeam : Match){
    //home
        Picasso.get().load(homeTeam.strTeamBadge).into(ivTeam1Logo)
        tvTeam1Name.text = homeTeam.strTeam
        if(match.intHomeScore.isNullOrEmpty()) tvTeam1Score.text = "-" else tvTeam1Score.text = match.intHomeShots
        if(match.intHomeShots.isNullOrEmpty()) home_shots.text = "-" else home_shots.text = match.intHomeShots
        if(match.strHomeLineupGoalkeeper.isNullOrEmpty())  home_goalkeeper.text = "-" else home_goalkeeper.text = match.strHomeLineupGoalkeeper?.replace(";","\n")
        if(match.strHomeLineupDefense.isNullOrEmpty())  home_defense.text = "-" else home_defense.text = match.strHomeLineupDefense?.replace(";","\n")
        if(match.strHomeLineupMidfield.isNullOrEmpty())  home_midfield.text = "-" else home_midfield.text = match.strHomeLineupMidfield?.replace(";","\n")
        if(match.strHomeLineupForward.isNullOrEmpty())  home_forward.text = "-" else home_forward.text = match.strHomeLineupForward?.replace(";","\n")
        if(match.strHomeLineupSubstitutes.isNullOrEmpty()) home_subtitutes.text = "-" else home_subtitutes.text = match.strHomeLineupSubstitutes?.replace(";","\n")
        //away
        Picasso.get().load(awayTeam.strTeamBadge).into(ivTeam2Logo)
        tvTeam2Name.text = match.strAwayTeam
        if(match.intAwayScore.isNullOrEmpty()) tvTeam2Score.text = "-" else tvTeam2Score.text = match.intAwayShots
        if(match.intAwayShots.isNullOrEmpty()) away_shots.text = "-" else away_shots.text = match.intAwayShots
        if(match.strAwayLineupGoalkeeper.isNullOrEmpty())  away_goalkeeper.text = "-" else away_goalkeeper.text = match.strAwayLineupGoalkeeper?.replace(";","\n")
        if(match.strAwayLineupDefense.isNullOrEmpty())  away_defense.text = "-" else away_defense.text = match.strAwayLineupDefense?.replace(";","\n")
        if(match.strAwayLineupMidfield.isNullOrEmpty())  away_midfield.text = "-" else away_midfield.text = match.strAwayLineupMidfield?.replace(";","\n")
        if(match.strAwayLineupForward.isNullOrEmpty())  away_forward.text = "-" else away_forward.text = match.strAwayLineupForward?.replace(";","\n")
        if(match.strAwayLineupSubstitutes.isNullOrEmpty()) away_subtitutes.text = "-" else away_subtitutes.text = match.strAwayLineupSubstitutes?.replace(";","\n")

        hideLoading()
    }
}

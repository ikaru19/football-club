package com.example.ikaru.footballclub.EventDetail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem

import com.example.ikaru.footballclub.model.Match
import com.example.ikaru.footballclub.R
import com.example.ikaru.footballclub.R.color.colorAccent
import com.example.ikaru.footballclub.api.SportApiMatch
import com.example.ikaru.footballclub.api.TheSportDBApi
import com.example.ikaru.footballclub.api.TheSportDBApiMatch
import com.example.ikaru.footballclub.db.Favorite
import com.example.ikaru.footballclub.db.database
import com.example.ikaru.footballclub.model.Team
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import com.example.ikaru.footballclub.R.drawable.ic_add_to_favorites
import com.example.ikaru.footballclub.R.drawable.ic_added_to_favorites
import com.example.ikaru.footballclub.util.changeFormatDate
import com.example.ikaru.footballclub.util.strToDate

class EventDetail : AppCompatActivity() , EventDetailView{
    private lateinit var event: Match
    private lateinit var presenter: EventDetailPresenter

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        event = intent.getParcelableExtra("Event")
        val date = strToDate(event.dateEvent)
        someDate.text = changeFormatDate(date)
        someDate.text = event.dateEvent
        tvTeam1Score.text = event.intHomeScore
        tvTeam1Name.text = event.strHomeTeam
        tvTeam2Score.text = event.intAwayScore
        tvTeam2Name.text = event.strAwayTeam

        favoriteState()

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
//        if(match.intHomeScore.isNullOrEmpty()) tvTeam1Score.text = "-" else tvTeam1Score.text = match.intHomeShots
        if(match.intHomeShots.isNullOrEmpty()) home_shots.text = "-" else home_shots.text = match.intHomeShots
        if(match.strHomeLineupGoalkeeper.isNullOrEmpty())  home_goalkeeper.text = "-" else home_goalkeeper.text = match.strHomeLineupGoalkeeper?.replace(";","\n")
        if(match.strHomeLineupDefense.isNullOrEmpty())  home_defense.text = "-" else home_defense.text = match.strHomeLineupDefense?.replace(";","\n")
        if(match.strHomeLineupMidfield.isNullOrEmpty())  home_midfield.text = "-" else home_midfield.text = match.strHomeLineupMidfield?.replace(";","\n")
        if(match.strHomeLineupForward.isNullOrEmpty())  home_forward.text = "-" else home_forward.text = match.strHomeLineupForward?.replace(";","\n")
        if(match.strHomeLineupSubstitutes.isNullOrEmpty()) home_subtitutes.text = "-" else home_subtitutes.text = match.strHomeLineupSubstitutes?.replace(";","\n")
        //away
        Picasso.get().load(awayTeam.strTeamBadge).into(ivTeam2Logo)
        tvTeam2Name.text = match.strAwayTeam
//        if(match.intAwayScore.isNullOrEmpty()) tvTeam2Score.text = "-" else tvTeam2Score.text = match.intAwayShots
        if(match.intAwayShots.isNullOrEmpty()) away_shots.text = "-" else away_shots.text = match.intAwayShots
        if(match.strAwayLineupGoalkeeper.isNullOrEmpty())  away_goalkeeper.text = "-" else away_goalkeeper.text = match.strAwayLineupGoalkeeper?.replace(";","\n")
        if(match.strAwayLineupDefense.isNullOrEmpty())  away_defense.text = "-" else away_defense.text = match.strAwayLineupDefense?.replace(";","\n")
        if(match.strAwayLineupMidfield.isNullOrEmpty())  away_midfield.text = "-" else away_midfield.text = match.strAwayLineupMidfield?.replace(";","\n")
        if(match.strAwayLineupForward.isNullOrEmpty())  away_forward.text = "-" else away_forward.text = match.strAwayLineupForward?.replace(";","\n")
        if(match.strAwayLineupSubstitutes.isNullOrEmpty()) away_subtitutes.text = "-" else away_subtitutes.text = match.strAwayLineupSubstitutes?.replace(";","\n")

        hideLoading()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if(isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to event.idEvent,
                    Favorite.EVENT_NAME to event.strEvent,
                    Favorite.EVENT_DATE to event.dateEvent,
                    Favorite.HOME_TEAM_ID to event.idHomeTeam,
                    Favorite.HOME_TEAM_NAME to event.strHomeTeam,
                    Favorite.HOME_TEAM_SCORE to event.intHomeScore,
                    Favorite.AWAY_TEAM_ID to event.idAwayTeam,
                    Favorite.AWAY_TEAM_NAME to event.strAwayTeam,
                    Favorite.AWAY_TEAM_SCORE to event.intAwayScore)
            }
            snackbar(detailSwipe, "Added to favorite").show()
        }catch (e: SQLiteConstraintException){
            snackbar(detailSwipe, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use{
                delete(Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})", "id" to event.idEvent.orEmpty())
            }
            snackbar(detailSwipe, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(detailSwipe, e.localizedMessage).show()
        }
    }

    private fun setFavorite(){
        val icon = if(isFavorite) ic_added_to_favorites else ic_add_to_favorites

        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, icon)
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(EVENT_ID = {id})", "id" to event.idEvent.orEmpty())
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

}

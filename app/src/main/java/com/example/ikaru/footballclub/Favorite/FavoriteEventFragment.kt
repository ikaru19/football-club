package com.example.ikaru.footballclub.Favorite

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ikaru.footballclub.EventDetail.EventDetail
import com.example.ikaru.footballclub.R
import com.example.ikaru.footballclub.db.Favorite
import com.example.ikaru.footballclub.db.database
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import com.example.ikaru.footballclub.model.Match

class FavoriteEventFragment : Fragment(), AnkoComponent<Context> {
    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var eventList: RecyclerView
    private lateinit var adapter: FavoriteEventAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteEventAdapter(favorites){
            val event = Match(
                idEvent = it.eventId,
                dateEvent = it.eventDate,
                strEvent = it.eventName,
                idHomeTeam = it.homeTeamId,
                strHomeTeam = it.homeTeam,
                intHomeScore = it.homeScore,
                idAwayTeam = it.awayTeamId,
                strAwayTeam = it.awayTeam,
                intAwayScore = it.awayScore
            )
            ctx.startActivity<EventDetail>("Event" to event)
        }
        eventList.adapter = adapter

        showFavorite()
        swipeRefresh.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = createView(
        AnkoContext.create(ctx))

    override fun createView(ui: AnkoContext<Context>) = with(ui){
        verticalLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                eventList = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }

        }
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}

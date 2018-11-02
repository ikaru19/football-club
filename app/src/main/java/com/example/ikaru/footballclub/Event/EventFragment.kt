package com.example.ikaru.footballclub.Event

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ikaru.footballclub.EventDetail.EventDetail
import com.example.ikaru.footballclub.R
import com.example.ikaru.footballclub.api.TheSportDBApiMatch
import com.example.ikaru.footballclub.api.TheSportApiNext
import com.example.ikaru.footballclub.api.TheSportDBApi
import com.example.ikaru.footballclub.model.Match
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class EventFragment : Fragment(){
    private var match : MutableList<Match> = mutableListOf()
    private lateinit var presenter: EventPresenter
    private lateinit var review: RecyclerView
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var adapter: EventAdapter
    private var nav = 1
    private var leagueId = "4328"
    private var api = TheSportDBApiMatch.getTeams(leagueId)

    companion object {
        fun newInstance(nav: Int, leagueId: String): EventFragment {
            val fragment = EventFragment()
            fragment.nav = nav
            fragment.leagueId = leagueId
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val theSportDBApi = TheSportDBApi(leagueId)
        if (nav == 1){
            api = theSportDBApi.getprevsechdule()
        }else{
            api  = theSportDBApi.getnextsechdule()
        }
        val gson = Gson()

        presenter = EventPresenter(this,api,gson)
        adapter = EventAdapter(match){
            ctx.startActivity<EventDetail>("Event" to it)
        }
        review.adapter = adapter

        swipe.onRefresh {
            presenter.getList()
        }

        presenter.getList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }
    fun showLoading() {
        swipe.isRefreshing = true
    }

    fun hideLoading() {
        swipe.isRefreshing = false
    }



    fun showList(data: List<Match>) {
        hideLoading()
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }

    fun createView(ui: AnkoContext<Context>) = with(ui){
        verticalLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(4)
            leftPadding = dip(4)
            rightPadding = dip(4)
            bottomPadding = dip(50)

            swipe = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                review = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }

        }
    }


}
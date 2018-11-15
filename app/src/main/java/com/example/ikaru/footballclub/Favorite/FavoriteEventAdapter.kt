package com.example.ikaru.footballclub.Favorite

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ikaru.footballclub.R
import com.example.ikaru.footballclub.db.Favorite
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteEventAdapter(private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit): RecyclerView.Adapter<FavoriteEventAdapter.FavoriteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(ScheduleUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount() = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }


    class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val matchDate: TextView = itemView.find(R.id.date)
        private val homeTeam: TextView = view.find(R.id.homeTeam)
        private val homeScore: TextView = view.find(R.id.homeScore)
        private val awayTeam: TextView = view.find(R.id.awayTeam)
        private val awayScore: TextView = view.find(R.id.awayscore)

        fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit){

            matchDate.text = favorite.eventDate

            homeTeam.text = favorite.homeTeam
            homeScore.text = favorite.homeScore

            awayTeam.text = favorite.awayTeam
            awayScore.text = favorite.awayScore

            itemView.onClick { listener(favorite) }
        }
    }

    class ScheduleUI: AnkoComponent<ViewGroup>{
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui){
            cardView {
                lparams(width = matchParent, height = wrapContent){
                    gravity = Gravity.CENTER
                    margin = dip(4)
                    radius = 4f
                }

                verticalLayout {

                    textView("Minggu, 04 Maret 2018"){
                        id = R.id.date
                    }.lparams(width = wrapContent, height = wrapContent){
                        gravity = Gravity.CENTER
                        margin = dip(8)
                    }

                    relativeLayout {

                        textView{
                            id = R.id.homeTeam
                            textSize = 14f
                            textColor = Color.BLACK
                            gravity = Gravity.END
                        }.lparams(width = wrapContent, height = wrapContent){
                            leftOf(R.id.homeScore)
                            rightMargin = dip(10)
                        }

                        textView{
                            id = R.id.homeScore
                            textSize = 12f
                            gravity = Gravity.CENTER
                            textColor = Color.BLACK
                        }.lparams(width = wrapContent, height = wrapContent){
                            leftOf(R.id.vs)
                        }

                        textView("vs"){
                            id = R.id.vs
                            textSize = 10f
                        }.lparams(width = wrapContent, height = wrapContent){
                            centerInParent()
                            leftMargin = dip(6)
                            rightMargin = dip(6)
                        }

                        textView{
                            id = R.id.awayscore
                            textSize = 12f
                            gravity = Gravity.CENTER
                            textColor = Color.BLACK
                        }.lparams(width = wrapContent, height = wrapContent){
                            rightOf(R.id.vs)
                        }

                        textView{
                            id = R.id.awayTeam
                            textSize = 14f
                            textColor = Color.BLACK
                            gravity = Gravity.START
                        }.lparams(width = wrapContent, height = wrapContent){
                            rightOf(R.id.awayscore)
                            leftMargin = dip(10)
                        }

                    }.lparams(width = matchParent, height = wrapContent)

                }.lparams(width = matchParent, height = wrapContent){
                    gravity = Gravity.CENTER
                    bottomMargin = dip(8)
                }
            }
        }

    }
}
package com.gdk.rio.footballschedule.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gdk.rio.footballschedule.R
import com.gdk.rio.footballschedule.model.Match

class MainAdapter (private val context: Context, private val matchs: List<Match>, private val listener: (Match) -> Unit)
    : RecyclerView.Adapter<MainAdapter.MatchViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match, parent, false))
    }

    override fun getItemCount(): Int {
        return matchs.size
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindMatchItem(matchs[position], listener)
    }


    class MatchViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val matchDate = view.findViewById<TextView>(R.id.tv_date)
        private val teamHome = view.findViewById<TextView>(R.id.tv_team1)
        private val teamAway = view.findViewById<TextView>(R.id.tv_team2)
        private val scoreHome = view.findViewById<TextView>(R.id.tv_score1)
        private val scoreAway = view.findViewById<TextView>(R.id.tv_score2)

        fun bindMatchItem (matchs: Match, listener: (Match) -> Unit){
            matchDate.text = matchs.matchDate
            teamHome.text = matchs.teamHome
            teamAway.text = matchs.teamAway
            scoreHome.text = matchs.scoreHome
            scoreAway.text = matchs.scoreAway
            itemView.setOnClickListener { listener(matchs) }
        }
    }
}
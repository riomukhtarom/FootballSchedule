package com.gdk.rio.footballschedule.view.main.favoritematch

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gdk.rio.footballschedule.R
import com.gdk.rio.footballschedule.database.FavoriteMatch
import com.gdk.rio.footballschedule.model.match.Match

class FavoriteMatchAdapter (private val context: Context, private val favoriteMatch: List<FavoriteMatch>, private val listener: (FavoriteMatch) -> Unit)
    : RecyclerView.Adapter<FavoriteMatchAdapter.MatchViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match, parent, false))
    }

    override fun getItemCount(): Int {
        return favoriteMatch.size
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindMatchItem(favoriteMatch[position], listener)
    }


    class MatchViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val matchDate = view.findViewById<TextView>(R.id.tv_date)
        private val teamHome = view.findViewById<TextView>(R.id.tv_team1)
        private val teamAway = view.findViewById<TextView>(R.id.tv_team2)
        private val scoreHome = view.findViewById<TextView>(R.id.tv_score1)
        private val scoreAway = view.findViewById<TextView>(R.id.tv_score2)

        fun bindMatchItem (favoriteMatch: FavoriteMatch, listener: (FavoriteMatch) -> Unit){
            matchDate.text = favoriteMatch.matchDate
            teamHome.text = favoriteMatch.homeTeam
            teamAway.text = favoriteMatch.awayTeam
            if(favoriteMatch.homeScore == "null"){
                scoreHome.text = ""
                scoreAway.text = ""
            }else{
                scoreHome.text = favoriteMatch.homeScore
                scoreAway.text = favoriteMatch.awayScore
            }
            itemView.setOnClickListener { listener(favoriteMatch) }
        }
    }
}
package com.gdk.rio.footballschedule.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.gdk.rio.footballschedule.R
import com.gdk.rio.footballschedule.model.Match
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewListMatch: RecyclerView
    private val matchItems: MutableList<Match> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeData()

        matchItems.size

        rv_schedule.layoutManager = LinearLayoutManager(this)
        Log.e("Main", matchItems.size.toString())
        rv_schedule.adapter = MainAdapter(this, matchItems) {
            toast("${it.teamHome}")
        }
    }

    private fun initializeData(){
        val matchDate = resources.getStringArray(R.array.date)
        val teamHome = resources.getStringArray(R.array.team1)
        val teamAway = resources.getStringArray(R.array.team2)
        val scoreHome = resources.getStringArray(R.array.score1)
        val scoreAway = resources.getStringArray(R.array.score2)
        matchItems.clear()

        for(index in matchDate.indices){
            matchItems.add(Match(matchDate[index], teamHome[index], teamAway[index], scoreHome[index], scoreAway[index]))
        }

        matchItems.size
    }
}

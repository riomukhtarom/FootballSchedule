package com.gdk.rio.footballschedule.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.gdk.rio.footballschedule.R
import com.gdk.rio.footballschedule.model.Match
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() , MainView {

    private val matchItems: MutableList<Match> = mutableListOf()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeData()

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

    }

    override fun showLoading() {
        swipeRefreshLayout.visibility = SwipeRefreshLayout.VISIBLE
    }

    override fun hideLoading() {
        swipeRefreshLayout.visibility = SwipeRefreshLayout.INVISIBLE
    }

    override fun showMatch() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

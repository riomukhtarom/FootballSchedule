package com.gdk.rio.footballschedule.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ProgressBar
import com.gdk.rio.footballschedule.R
import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.api.TheSportDBApi
import com.gdk.rio.footballschedule.model.Match
import com.gdk.rio.footballschedule.presenter.MainPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.colorAttr
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() , MainView {


    private val matchItems: MutableList<Match> = mutableListOf()
    private lateinit var mainAdapter: MainAdapter
    private lateinit var presenter: MainPresenter
    private val LAST_EVENT: String = "eventspastleague.php"
    private val NEXT_EVENT: String = "eventsnextleague.php"
    private val LEAGUE_ID: String = "4328"
    val api = ApiRepository()
    val gson = Gson()
    private lateinit var event: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        event = LAST_EVENT

        presenter = MainPresenter(this, api, gson)
        presenter.getMatchList(event, LEAGUE_ID)

        mainAdapter = MainAdapter(this, matchItems){
            startActivity<DetailActivity>("date" to "Today")
        }
        rv_schedule.layoutManager = LinearLayoutManager(this)
        rv_schedule.adapter = mainAdapter

        ln_result_match.onClick {
            event = LAST_EVENT
            presenter.getMatchList(event, LEAGUE_ID)
        }
        ln_next_match.onClick {
            event = NEXT_EVENT
            presenter.getMatchList(event, LEAGUE_ID)
        }
    }

    override fun showLoading() {
        progress_bar.visibility = ProgressBar.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = ProgressBar.INVISIBLE
    }

    override fun showMatch(matchList: List<Match>) {
        matchItems.clear()
        matchItems.addAll(matchList)
        mainAdapter.notifyDataSetChanged()
    }

}

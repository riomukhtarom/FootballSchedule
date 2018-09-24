package com.gdk.rio.footballschedule.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.widget.ProgressBar
import com.gdk.rio.footballschedule.R
import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.model.match.Match
import com.gdk.rio.footballschedule.presenter.MainPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() , MainView {


    private val matchItems: MutableList<Match> = mutableListOf()
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mainPresenter: MainPresenter
    private val LAST_EVENT: String = "eventspastleague.php"
    private val NEXT_EVENT: String = "eventsnextleague.php"
    private val LEAGUE_ID: String = "4328"
    private val api = ApiRepository()
    private val gson = Gson()
    private lateinit var event: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        event = LAST_EVENT
        initialize()

        mainAdapter = MainAdapter(this, matchItems){
            startActivity<DetailActivity>("events" to it)
        }
        rv_schedule.layoutManager = LinearLayoutManager(this)
        rv_schedule.adapter = mainAdapter

        ln_result_match.onClick {
            event = LAST_EVENT
            mainPresenter.getMatchList(event, LEAGUE_ID)

            //set result match color
            iv_result_match.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.colorWhite))
            tv_result_match.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorWhite))

            //set next match color
            iv_next_match.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.colorBlack))
            tv_next_match.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorBlack))
        }

        ln_next_match.onClick {
            event = NEXT_EVENT
            mainPresenter.getMatchList(event, LEAGUE_ID)

            //set result match color
            iv_result_match.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.colorBlack))
            tv_result_match.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorBlack))

            //set next match color
            iv_next_match.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.colorWhite))
            tv_next_match.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorWhite))
        }
    }

    override fun showLoading() {
        pb_main.visibility = ProgressBar.VISIBLE
    }

    override fun hideLoading() {
        pb_main.visibility = ProgressBar.INVISIBLE
    }

    override fun showMatch(matchList: List<Match>) {
        matchItems.clear()
        matchItems.addAll(matchList)
        mainAdapter.notifyDataSetChanged()
    }

    private fun initialize(){
        mainPresenter = MainPresenter(this, api, gson)
        mainPresenter.getMatchList(event, LEAGUE_ID)

        //set result match color
        iv_result_match.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.colorWhite))
        tv_result_match.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorWhite))

        //set next match color
        iv_next_match.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.colorBlack))
        tv_next_match.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorBlack))
    }

}

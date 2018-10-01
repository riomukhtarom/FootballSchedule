package com.gdk.rio.footballschedule.view.main.nextmatch


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.gdk.rio.footballschedule.R
import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.model.match.Match
import com.gdk.rio.footballschedule.presenter.NextMatchPresenter
import com.gdk.rio.footballschedule.view.detailmatch.DetailActivity
import com.gdk.rio.footballschedule.view.main.MatchView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class NextMatchFragment : Fragment(), MatchView {

    private val matchItems: MutableList<Match> = mutableListOf()
    private lateinit var nextMatchAdapter: NextMatchAdapter
    private lateinit var nextMatchPresenter: NextMatchPresenter
    private val NEXT_EVENT: String = "eventsnextleague.php"
    private val LEAGUE_ID: String = "4328"
    private val api = ApiRepository()
    private val gson = Gson()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nextMatchPresenter = NextMatchPresenter(this, api, gson)
        nextMatchPresenter.getMatchList(NEXT_EVENT, LEAGUE_ID)

        nextMatchAdapter = NextMatchAdapter(activity!!, matchItems) {
            startActivity<DetailActivity>(
                    "matchId" to it.matchId,
                    "homeId" to it.homeId,
                    "awayId" to it.awayId)
        }
        rv_nextMatch.layoutManager = LinearLayoutManager(activity)
        rv_nextMatch.adapter = nextMatchAdapter

        sr_nextMatch.onRefresh {
            sr_nextMatch.isRefreshing = false
            nextMatchPresenter.getMatchList(NEXT_EVENT, LEAGUE_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun showLoading() {
        pb_nextMatch.visibility = ProgressBar.VISIBLE
    }

    override fun hideLoading() {
        pb_nextMatch.visibility = ProgressBar.INVISIBLE
    }

    override fun showMatch(matchList: List<Match>) {
        matchItems.clear()
        matchItems.addAll(matchList)
        nextMatchAdapter.notifyDataSetChanged()
    }
}

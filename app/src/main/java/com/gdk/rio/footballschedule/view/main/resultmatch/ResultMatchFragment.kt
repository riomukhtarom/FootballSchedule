package com.gdk.rio.footballschedule.view.main.resultmatch


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
import com.gdk.rio.footballschedule.view.main.MatchView
import com.gdk.rio.footballschedule.presenter.ResultMatchPresenter
import com.gdk.rio.footballschedule.view.detailmatch.DetailActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_result_match.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity


class ResultMatchFragment : Fragment(), MatchView {

    private val matchItems: MutableList<Match> = mutableListOf()
    private lateinit var resultMatchAdapter: ResultMatchAdapter
    private lateinit var resultMatchPresenter: ResultMatchPresenter
    private val LAST_EVENT: String = "eventspastleague.php"
    private val LEAGUE_ID: String = "4328"
    private val api = ApiRepository()
    private val gson = Gson()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        resultMatchPresenter = ResultMatchPresenter(this, api, gson)
        resultMatchPresenter.getMatchList(LAST_EVENT, LEAGUE_ID)

        resultMatchAdapter = ResultMatchAdapter(activity!!, matchItems) {
            startActivity<DetailActivity>(
                    "matchId" to it.matchId,
                    "homeId" to it.homeId,
                    "awayId" to it.awayId)
        }
        rv_resultMatch.layoutManager = LinearLayoutManager(activity)
        rv_resultMatch.adapter = resultMatchAdapter

        sr_resultMatch.onRefresh {
            sr_resultMatch.isRefreshing = false
            resultMatchPresenter.getMatchList(LAST_EVENT, LEAGUE_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_match, container, false)
    }

    override fun showLoading() {
        pb_resultMatch.visibility = ProgressBar.VISIBLE
    }

    override fun hideLoading() {
        pb_resultMatch.visibility = ProgressBar.INVISIBLE
    }

    override fun showMatch(matchList: List<Match>) {
        matchItems.clear()
        matchItems.addAll(matchList)
        resultMatchAdapter.notifyDataSetChanged()
    }


}

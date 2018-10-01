package com.gdk.rio.footballschedule.view.main.favoritematch


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.gdk.rio.footballschedule.R
import com.gdk.rio.footballschedule.api.ApiRepository
import com.gdk.rio.footballschedule.database.FavoriteMatch
import com.gdk.rio.footballschedule.database.database
import com.gdk.rio.footballschedule.model.match.Match
import com.gdk.rio.footballschedule.presenter.NextMatchPresenter
import com.gdk.rio.footballschedule.view.detailmatch.DetailActivity
import com.gdk.rio.footballschedule.view.main.nextmatch.NextMatchAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity


class FavoriteMatchFragment : Fragment() {

    private val matchItems: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var favoriteMatchAdapter: FavoriteMatchAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favoriteMatchAdapter = FavoriteMatchAdapter(activity!!, matchItems) {
            startActivity<DetailActivity>(
                    "matchId" to it.matchId,
                    "homeId" to it.homeId,
                    "awayId" to it.awayId)
        }
        rv_favoriteMatch.layoutManager = LinearLayoutManager(activity)
        rv_favoriteMatch.adapter = favoriteMatchAdapter

        matchItems.clear()
        showFavoriteMatch()

        sr_favoriteMatch.onRefresh {
            matchItems.clear()
            showFavoriteMatch()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }

    private fun showFavoriteMatch(){
        context?.database?.use {
            sr_favoriteMatch.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            matchItems.addAll(favorite)
            favoriteMatchAdapter.notifyDataSetChanged()
        }
    }


}

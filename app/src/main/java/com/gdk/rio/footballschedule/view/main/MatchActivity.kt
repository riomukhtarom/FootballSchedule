package com.gdk.rio.footballschedule.view.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gdk.rio.footballschedule.R
import com.gdk.rio.footballschedule.R.id.*
import com.gdk.rio.footballschedule.view.main.favoritematch.FavoriteMatchFragment
import com.gdk.rio.footballschedule.view.main.nextmatch.NextMatchFragment
import com.gdk.rio.footballschedule.view.main.resultmatch.ResultMatchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MatchActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationMain.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                item_result_match -> {
                    loadResultMatchFragment(savedInstanceState)
                }

                item_next_match -> {
                    loadNextMatchFragment(savedInstanceState)
                }

                item_favorite_match -> {
                    loadFavoriteMatchFragment(savedInstanceState)
                }
            }

            true
        }

        bottomNavigationMain.selectedItemId = item_result_match
    }

    private fun loadResultMatchFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_mainFrame, ResultMatchFragment(), ResultMatchFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadNextMatchFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_mainFrame, NextMatchFragment(), NextMatchFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadFavoriteMatchFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_mainFrame, FavoriteMatchFragment(), FavoriteMatchFragment::class.java.simpleName)
                    .commit()
        }
    }

}

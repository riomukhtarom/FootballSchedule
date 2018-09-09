package com.gdk.rio.footballschedule.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import org.jetbrains.anko.*
import com.gdk.rio.footballschedule.view.MainActivity
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivityUI: AnkoComponent<MainActivity> {

    private lateinit var  recyclerViewListMatch: RecyclerView

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        linearLayout{
            lparams{
                width = matchParent
                height = matchParent
                orientation = LinearLayout.VERTICAL
                padding = dip(8)
            }

            recyclerViewListMatch = recyclerView {
                layoutManager = LinearLayoutManager(ctx)
            }.lparams{
                width = matchParent
                height = matchParent
            }
        }
    }

}

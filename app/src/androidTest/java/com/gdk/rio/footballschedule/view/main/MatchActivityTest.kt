package com.gdk.rio.footballschedule.view.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeDown
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.gdk.rio.footballschedule.R.id.*
import com.gdk.rio.footballschedule.view.detailmatch.DetailActivity
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

class MatchActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(MatchActivity::class.java)

    private val timeRequest = 3000L

    @Test
    fun testAppBehaviour() {
        //Main Match Test
        onView(withId(frame_mainFrame))
                .check(matches(isDisplayed()))
        onView(withId(bottomNavigationMain))
                .check(matches(isDisplayed()))

        //Result Match Test
        onView(withId(item_result_match))
                .perform(click())
        testRecyclerViewBehaviour(rv_resultMatch,13)
        testDetailViewBehaviour(rv_resultMatch, "Add to favorite")

        //Next Match Test
        onView(withId(item_next_match))
                .perform(click())
        testRecyclerViewBehaviour(rv_nextMatch,12)
        testDetailViewBehaviour(rv_nextMatch, "Add to favorite")

        //Favorite Match Test
        onView(withId(item_favorite_match))
                .perform(click())
        testRecyclerViewBehaviour(rv_favoriteMatch,0)
        testDetailViewBehaviour(rv_favoriteMatch, "Removed from favorite")
        testRecyclerViewBehaviour(rv_favoriteMatch,0)
        testDetailViewBehaviour(rv_favoriteMatch, "Removed from favorite")
    }

    private fun testRecyclerViewBehaviour(id: Int, position: Int) {
        sleep(timeRequest)
        onView(withId(id))
                .check(matches(isDisplayed()))
        onView(withId(id))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(position))
        onView(withId(id))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    private fun testDetailViewBehaviour(idRecyclerView: Int, text: String) {
        onView(withId(sv_detail))
                .check(matches(isDisplayed()))
        sleep(timeRequest)
        onView(withId(iv_homeLogo))
                .check(matches(isDisplayed()))
        onView(withId(iv_awayLogo))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite))
                .perform(click())
        onView(withText(text))
                .check(matches(isDisplayed()))
        pressBack()
        onView(withId(idRecyclerView))
                .perform(swipeDown())
        onView(withId(idRecyclerView))
                .check(matches(isDisplayed()))
    }
}
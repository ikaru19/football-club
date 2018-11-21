package com.example.ikaru.footballclub

import android.support.design.R.id.snackbar_text
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.ikaru.footballclub.R.id.*

import com.example.ikaru.footballclub.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivity {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testAppBehaviour(){

        Thread.sleep(15000)
        onView(withId(rv_event_list))
            .check(matches(isDisplayed()))
        onView(withId(rv_event_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        Thread.sleep(500)
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withId(snackbar_text))
        onView(withText("Added to favorite"))
            .check(matches(isDisplayed()))
        pressBack()
        Thread.sleep(500)
        onView(withId(navigation_favorite)).perform(click())
    }
}
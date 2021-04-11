package com.shreyas.musicsearch.view

import android.os.SystemClock
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shreyas.musicsearch.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrackSearchFragmentTest {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_initial_search_screen() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.music_search_view)).perform(click())

        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("Pink Floyd"),
            pressImeActionButton()
        )
        SystemClock.sleep(3000)
        with(onView(withId(R.id.music_search_list))) {
            perform(
                RecyclerViewActions.scrollToPosition<TrackListAdapter.TrackViewHolder>(2)
            )
            perform(click())
        }
        SystemClock.sleep(3000)
    }

    @Test
    fun test_scroll_to_twenty_fifth_and_click() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.music_search_view)).perform(click())

        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("Pink Floyd"),
            pressImeActionButton()
        )
        SystemClock.sleep(6000)
        with(onView(withId(R.id.music_search_list))) {
            perform(
                RecyclerViewActions.actionOnItemAtPosition<TrackListAdapter.TrackViewHolder>(
                    24,
                    click()
                )
            )
        }
        SystemClock.sleep(3000)
    }
}
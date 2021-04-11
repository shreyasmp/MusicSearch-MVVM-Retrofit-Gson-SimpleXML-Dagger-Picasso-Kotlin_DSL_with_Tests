package com.shreyas.musicsearch.view

import android.os.SystemClock
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shreyas.musicsearch.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrackLyricFragmentTest {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_scroll_to_first_search_item_and_scroll_lyrics() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.music_search_view)).perform(click())

        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("Led Zeppelin"),
            pressImeActionButton()
        )
        SystemClock.sleep(3000)
        with(onView(withId(R.id.music_search_list))) {
            perform(
                RecyclerViewActions.scrollToPosition<TrackListAdapter.TrackViewHolder>(0)
            )
            perform(click())
            SystemClock.sleep(5000)
        }
        // Check if lyrics screen is displayed
        onView(withId(R.id.track_lyric_card)).check(matches(isDisplayed()))
        SystemClock.sleep(3000)
        // Then scroll down or swipe up on lyrics to see full and close
        onView(withId(R.id.lyric_scroll_view)).perform(swipeUp())
        SystemClock.sleep(3000)
    }

    @Test
    fun test_back_button_to_music_search_list() {
        test_scroll_to_first_search_item_and_scroll_lyrics()
        SystemClock.sleep(6000)
        Espresso.pressBack()
        onView(withId(R.id.music_search_list)).check(matches(isDisplayed()))
    }
}
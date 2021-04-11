package com.shreyas.musicsearch.view

import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import com.google.common.truth.Truth.assertThat
import com.shreyas.musicsearch.R
import com.shreyas.musicsearch.runner.MusicRobolectricTestRunner
import com.shreyas.musicsearch.utils.TestJsonUtils.startFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode

@RunWith(MusicRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class TrackSearchFragmentRoboTest {

    private lateinit var trackSearchFragment: TrackSearchFragment
    private lateinit var navHostFragment: NavHostFragment

    @Before
    fun setUp() {
        trackSearchFragment = TrackSearchFragment()
        navHostFragment = NavHostFragment()
        Shadows.shadowOf(Looper.getMainLooper()).idle()
    }

    @Test
    fun `test if search track fragment is created`() {
        startFragment(trackSearchFragment)
        assertThat(trackSearchFragment).isNotNull()
    }

    @Test
    fun `test if the first fragment is visible`() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity ->
            activity.supportFragmentManager.let { fragmentManager ->
                fragmentManager.executePendingTransactions()
                val targetFragment = fragmentManager.findFragmentById(R.id.fragment_container)
                assertThat(targetFragment?.isVisible).isTrue()
            }
        }
    }
}
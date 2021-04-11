package com.shreyas.musicsearch.viewmodel

import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.shreyas.musicsearch.base.BaseViewModelTest
import com.shreyas.musicsearch.model.TrackLyrics
import com.shreyas.musicsearch.utils.TestJsonUtils.getObjectFromJsonFile
import com.shreyas.musicsearch.utils.TestJsonUtils.getObjectFromXmlFile
import com.shreyas.musicsearch.utils.testObserver
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class TrackLyricViewModelTest : BaseViewModelTest() {

    @SpyK
    private lateinit var viewModel: TrackLyricViewModel

    @Mock
    private lateinit var trackSearchObserver: Observer<TrackLyrics>

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = TrackLyricViewModel(repository)
    }

    @Test
    fun `view model is not null`() {
        assertThat(viewModel).isNotNull()
    }

    @Test
    fun `track lyric result live data is null at first`() {
        val trackLyric = viewModel.trackLyric.testObserver()
        assertThat(trackLyric.observedValues).isEmpty()
    }

    @Test
    fun `track lyric result live data has success data`() {
        val trackLyrics = getObjectFromXmlFile(
            xmlFile = "success_lyric_results.xml",
            tClass = TrackLyrics::class.java
        )
        viewModel.trackLyric.value = trackLyrics
        val trackLyricsLiveData = viewModel.trackLyric.testObserver()
        assertThat(trackLyricsLiveData.observedValues).containsExactly(trackLyrics)
    }

    @Test
    fun `track lyric result live data has error`() {
        val error = null
        viewModel.trackLyric.value = error
        val errorListLiveData = viewModel.trackLyric.testObserver()
        assertThat(errorListLiveData.observedValues).containsExactly(error)
    }

    @Test
    fun `on http success fetch track lyric results is as expected`() {
        coroutineTestRule.runBlockingTest {
            val response = getObjectFromXmlFile(
                xmlFile = "success_lyric_results.xml",
                tClass = TrackLyrics::class.java
            )
            viewModel.trackLyric.value = response
            viewModel.trackLyric.observeForever(trackSearchObserver)

            doReturn(viewModel.trackLyric).`when`(repository)
                .getTrackLyric(anyString(), anyString())

            viewModel.fetchTrackLyric("pink floyd", "time")

            assertThat(viewModel.trackLyric.value).isEqualTo(response)

            verify(repository).getTrackLyric("pink floyd", "time")
        }
    }

    @Test
    fun `on http error fetch track lyric results is null`() {
        coroutineTestRule.runBlockingTest {
            val response = getObjectFromJsonFile(
                jsonFile = "success_malformed_results.xml",
                tClass = TrackLyrics::class.java
            )
            viewModel.trackLyric.value = response
            viewModel.trackLyric.observeForever(trackSearchObserver)

            doThrow(RuntimeException::class.java).`when`(repository)
                .getTrackLyric(anyString(), anyString())

            viewModel.fetchTrackLyric("pink floyd", "time")

            assertThat(viewModel.trackLyric.value).isNull()

            verify(repository).getTrackLyric(anyString(), anyString())
        }
    }

    @After
    fun tearDown() {
        viewModel.trackLyric.removeObserver(trackSearchObserver)
    }
}
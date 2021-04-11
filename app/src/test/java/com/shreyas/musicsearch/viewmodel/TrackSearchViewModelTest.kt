package com.shreyas.musicsearch.viewmodel

import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.shreyas.musicsearch.base.BaseViewModelTest
import com.shreyas.musicsearch.model.TrackDetail
import com.shreyas.musicsearch.model.TrackSearchResults
import com.shreyas.musicsearch.utils.TestJsonUtils.getObjectFromJsonFile
import com.shreyas.musicsearch.utils.testObserver
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class TrackSearchViewModelTest : BaseViewModelTest() {

    @SpyK
    private lateinit var viewModel: TrackSearchViewModel

    @Mock
    private lateinit var trackSearchObserver: Observer<List<TrackDetail>>

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = TrackSearchViewModel(repository)
    }

    @Test
    fun `view model is not null`() {
        assertThat(viewModel).isNotNull()
    }

    @Test
    fun `track search result list live data is null at first`() {
        val trackSearchResultList = viewModel.trackSearchList.testObserver()
        assertThat(trackSearchResultList.observedValues).isEmpty()
    }

    @Test
    fun `track search list live data has success data`() {
        val trackSearchResult = getObjectFromJsonFile(
            jsonFile = "success_search_results.json",
            tClass = TrackSearchResults::class.java
        )
        viewModel._trackSearchList.value = trackSearchResult?.results
        val trackSearchResultsLiveData = viewModel.trackSearchList.testObserver()
        assertThat(trackSearchResultsLiveData.observedValues).containsExactly(trackSearchResult?.results)
    }

    @Test
    fun `track search list live data has error`() {
        val error = null
        viewModel._trackSearchList.value = error
        val errorListLiveData = viewModel.trackSearchList.testObserver()
        assertThat(errorListLiveData.observedValues).containsExactly(error)
    }

    @Test
    fun `on http success fetch track search results is as expected`() {
        coroutineTestRule.runBlockingTest {
            val response = getObjectFromJsonFile(
                jsonFile = "success_search_results.json",
                tClass = TrackSearchResults::class.java
            )
            viewModel._trackSearchList.value = response?.results
            viewModel.trackSearchList.observeForever(trackSearchObserver)

            doReturn(viewModel.trackSearchList).`when`(repository)
                .getTrackSearchList(anyString())

            viewModel.fetchTrackSearchList("pink floyd")

            assertThat(viewModel.trackSearchList.value).isEqualTo(response?.results)

            verify(repository).getTrackSearchList(anyString())
        }
    }

    @Test
    fun `on http error fetch track search results is empty`() {
        coroutineTestRule.runBlockingTest {
            val response = getObjectFromJsonFile(
                jsonFile = "success_empty_results.json",
                tClass = TrackSearchResults::class.java
            )
            viewModel._trackSearchList.value = response?.results
            viewModel.trackSearchList.observeForever(trackSearchObserver)

            doThrow(RuntimeException::class.java).`when`(repository)
                .getTrackSearchList(anyString())

            viewModel.fetchTrackSearchList("pink floyd")

            assertThat(viewModel.trackSearchList.value).isEmpty()

            verify(repository).getTrackSearchList(anyString())
        }
    }

    @After
    fun tearDown() {
        viewModel.trackSearchList.removeObserver(trackSearchObserver)
    }
}
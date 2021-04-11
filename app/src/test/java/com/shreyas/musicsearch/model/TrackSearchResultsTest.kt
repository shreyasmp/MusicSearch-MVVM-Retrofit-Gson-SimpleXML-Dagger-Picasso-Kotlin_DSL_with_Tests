package com.shreyas.musicsearch.model

import com.google.common.truth.Truth.assertThat
import com.shreyas.musicsearch.utils.TestJsonUtils.getObjectFromJsonFile
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TrackSearchResultsTest {

    @Test
    fun copy() {
        val trackResultsResponse = getObjectFromJsonFile(
            jsonFile = "success_search_results.json",
            TrackSearchResults::class.java
        )
        val originalResponse = trackResultsResponse?.results?.let { TrackSearchResults(50, it) }
        val copiedResponse = trackResultsResponse?.let { originalResponse?.copy(50, it.results) }
        assertThat(copiedResponse).isEqualTo(originalResponse)
    }
}
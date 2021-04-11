package com.shreyas.musicsearch.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TrackLyricsTest {

    @Test
    fun copy() {
        val originalResponse = TrackLyrics(
            trackID = 0,
            checksum = "e7d77b8648f8c4eb4c79c7749f31813e",
            lyricID = 488,
            songName = "Comfortably Numb",
            artistName = "Pink Floyd",
            lyricURL = "http://www.chartlyrics.com/mBjZg2N310ewO7khMjdcRw/Comfortably+Numb.aspx",
            lyricsCoverArtURL = "http://ec1.images-amazon.com/images/P/B000X1LH6C.01.MZZZZZZZ.jpg",
            lyricRank = 9,
            lyricCorrectURL = "http://www.chartlyrics.com/app/correct.aspx?lid=NAA4ADgA",
            lyric = "Hello Is there anybody in there? Just nod if you can hear me. Is there anyone home? Come on,...."
        )
        val copiedResponse = originalResponse.copy()
        assertThat(copiedResponse).isEqualTo(originalResponse)
    }
}
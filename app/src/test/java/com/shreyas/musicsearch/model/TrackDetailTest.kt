package com.shreyas.musicsearch.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TrackDetailTest {

    @Test
    fun copy() {
        val originalResponse = TrackDetail(
            wrapperType = "track",
            kind = "song",
            artistId = 487143,
            collectionId = 1065975633,
            trackId = 1065976170,
            artistName = "Pink Floyd",
            collectionName = "The Wall",
            trackName = "Comfortably Numb",
            collectionCensoredName = "The Wall",
            trackCensoredName = "Comfortably Numb",
            artistViewUrl = "https://music.apple.com/us/artist/pink-floyd/487143?uo=4",
            collectionViewUrl = "https://music.apple.com/us/album/comfortably-numb/1065975633?i=1065976170&uo=4",
            trackViewUrl = "https://music.apple.com/us/album/comfortably-numb/1065975633?i=1065976170&uo=4",
            previewUrl = "https://audio-ssl.itunes.apple.com/itunes-assets/Music7/v4/8c/3b/31/8c3b3174-b79b-ddf8-707c-1ba7016da538/mzaf_7277949113137423986.plus.aac.p.m4a",
            artworkUrl30 = "https://is3-ssl.mzstatic.com/image/thumb/Music114/v4/c6/05/6d/c6056d6d-f71f-5e7e-f3d2-5d18da0c959e/source/30x30bb.jpg",
            artworkUrl60 = "https://is3-ssl.mzstatic.com/image/thumb/Music114/v4/c6/05/6d/c6056d6d-f71f-5e7e-f3d2-5d18da0c959e/source/60x60bb.jpg",
            artworkUrl100 = "https://is3-ssl.mzstatic.com/image/thumb/Music114/v4/c6/05/6d/c6056d6d-f71f-5e7e-f3d2-5d18da0c959e/source/100x100bb.jpg",
            collectionPrice = 19.99,
            trackPrice = 1.29,
            releaseDate = "1979-11-30T08:00:00Z",
            collectionExplicitness = "notExplicit",
            trackExplicitness = "notExplicit",
            discCount = 2,
            discNumber = 2,
            trackCount = 13,
            trackNumber = 6,
            trackTimeMillis = 382297,
            country = "USA",
            currency = "USD",
            primaryGenreName = "Rock",
            isStreamable = true
        )
        val copiedResponse = originalResponse.copy()
        assertThat(copiedResponse).isEqualTo(originalResponse)
    }
}
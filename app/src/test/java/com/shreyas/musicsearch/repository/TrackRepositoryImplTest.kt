package com.shreyas.musicsearch.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.shreyas.musicsearch.base.MockServerBaseTest
import com.shreyas.musicsearch.service.LyricService
import com.shreyas.musicsearch.service.MusicService
import com.shreyas.musicsearch.utils.ResultWrapper
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

@RunWith(JUnit4::class)
class TrackRepositoryImplTest : MockServerBaseTest() {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    override fun isMockServerEnabled(): Boolean = true

    private lateinit var repositoryImpl: TrackRepositoryImpl
    private lateinit var musicService: MusicService
    private lateinit var lyricService: LyricService

    @Before
    fun setUp() {
        musicService = provideTestMusicService()
        lyricService = provideTestLyricService()
        repositoryImpl = TrackRepositoryImpl(musicService, lyricService)
    }

    @Test
    fun `given response is 200 when fetching track search and returns success response`() {
        runBlockingTest {
            mockHttpResponseFromFile("success_search_results.json", HttpsURLConnection.HTTP_OK)
            when (val result = repositoryImpl.getTrackSearchList("pink floyd")) {
                is ResultWrapper.SUCCESS -> {
                    val searchResult = result.value.value
                    assertThat(searchResult).isNotNull()
                    assertThat(searchResult?.results?.size).isEqualTo(50)
                }
            }

        }
    }

    @Test
    fun `given response is 200 when fetching track search and returns empty response`() {
        runBlockingTest {
            mockHttpResponseFromFile("success_empty_results.json", HttpsURLConnection.HTTP_OK)
            when (val result = repositoryImpl.getTrackSearchList("led zippelon")) {
                is ResultWrapper.SUCCESS -> {
                    val searchResult = result.value.value
                    assertThat(searchResult).isNotNull()
                    assertThat(searchResult?.results?.size).isEqualTo(0)
                }
            }
        }
    }

    @Test
    fun `given response is 403 when fetching track search and returns exception`() {
        runBlockingTest {
            mockHttpResponse(403)
            when (val result = repositoryImpl.getTrackSearchList("linkin park")) {
                is ResultWrapper.FAILURE -> {
                    assertThat(result).isNotNull()
                    val expectedResponse = ResultWrapper.FAILURE(null)
                    assertThat(expectedResponse.code).isEqualTo((result).code)
                }
            }
        }
    }

    @Test
    fun `given response is 200 when fetching lyric for song and returns success response`() {
        runBlockingTest {
            mockHttpResponseFromFile("success_lyric_results.xml", HttpURLConnection.HTTP_OK)
            when (val result = repositoryImpl.getTrackLyric("eminem", "stan")) {
                is ResultWrapper.SUCCESS -> {
                    val lyrics = result.value.value
                    assertThat(lyrics).isNotNull()
                    assertThat(lyrics?.songName).isEqualTo("Comfortably Numb")
                }
            }
        }
    }

    @Test
    fun `given response is 200 when fetching lyrics for song and returns malformed response`() {
        runBlockingTest {
            mockHttpResponseFromFile("success_malformed_results.xml", HttpURLConnection.HTTP_OK)
            when (val result = repositoryImpl.getTrackLyric("deep purple", "hush")) {
                is ResultWrapper.SUCCESS -> {
                    val lyrics = result.value.value
                    assertThat(lyrics?.lyricsCoverArtURL).isNull()
                    assertThat(lyrics?.artistName).isEqualTo("Pink Floyd")
                }
            }
        }
    }

    @Test
    fun `given response is 403 when fetching lyrics for song and returns exception`() {
        runBlockingTest {
            mockHttpResponse(403)
            when (val result = repositoryImpl.getTrackLyric("micheal jackson", "bad")) {
                is ResultWrapper.FAILURE -> {
                    assertThat(result).isNotNull()
                    val expectedResponse = ResultWrapper.FAILURE(null)
                    assertThat(expectedResponse.code).isEqualTo((result).code)
                }
            }
        }
    }
}
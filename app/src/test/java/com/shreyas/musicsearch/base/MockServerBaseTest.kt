package com.shreyas.musicsearch.base

import com.shreyas.musicsearch.service.LyricService
import com.shreyas.musicsearch.service.MusicService
import com.shreyas.musicsearch.utils.TestJsonUtils.getResponseAsString
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

abstract class MockServerBaseTest {

    private lateinit var mockServer: MockWebServer

    @Before
    open fun setup() {
        this.startMockServer()
    }

    abstract fun isMockServerEnabled(): Boolean

    open fun startMockServer() {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun stopMockServer() {
        if (isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    @After
    open fun tearDown() {
        this.stopMockServer()
    }

    // Methods to mock responses based on responseCode and response json in assets
    open fun mockHttpResponseFromFile(fileName: String, responseCode: Int) =
        mockServer.enqueue(
            MockResponse().setResponseCode(responseCode).setBody(getResponseAsString(fileName))
        )

    open fun mockHttpResponse(responseCode: Int) =
        mockServer.enqueue(MockResponse().setResponseCode(responseCode))

    // Create a test music service
    fun provideTestMusicService(): MusicService {
        return Retrofit.Builder().baseUrl(mockServer.url("/")).addConverterFactory(
            GsonConverterFactory.create()
        ).client(OkHttpClient.Builder().build()).build().create(MusicService::class.java)
    }

    // Create a test lyric service
    fun provideTestLyricService(): LyricService {
        return Retrofit.Builder().baseUrl(mockServer.url("/"))
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(OkHttpClient.Builder().build()).build().create(LyricService::class.java)

    }
}
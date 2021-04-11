package com.shreyas.musicsearch.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shreyas.musicsearch.service.LyricService
import com.shreyas.musicsearch.service.MusicService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

@Module
object ServiceModule {

    private const val MUSIC_SEARCH_API_URL = "https://itunes.apple.com"

    private const val TRACK_LYRIC_API_URL = "http://api.chartlyrics.com/apiv1.asmx/"

    @Provides
    internal fun provideMusicService(okHttpClient: OkHttpClient): MusicService {
        return Retrofit.Builder()
            .baseUrl(MUSIC_SEARCH_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(MusicService::class.java)
    }

    @Provides
    internal fun providesMusicLyrics(okHttpClient: OkHttpClient): LyricService {
        return Retrofit.Builder()
            .baseUrl(TRACK_LYRIC_API_URL)
            // Since the output from URL is going to be a XML document, we use XML Factory
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(LyricService::class.java)
    }

    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    internal fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}
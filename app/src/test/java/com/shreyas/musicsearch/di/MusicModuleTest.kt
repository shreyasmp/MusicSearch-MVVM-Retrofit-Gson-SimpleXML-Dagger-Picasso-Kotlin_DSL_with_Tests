package com.shreyas.musicsearch.di

import com.google.common.truth.Truth.assertThat
import com.shreyas.musicsearch.MainApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.time.Instant

class MusicModuleTest {

    private val mockApplication = mockk<MainApplication>()
    private lateinit var module: MusicModule

    @Before
    fun setUp() {
        module = FakeMusicModule()
    }

    @Test
    fun `test provide context returns expected application context`() {
        every { mockApplication.applicationContext } returns mockApplication

        val result = MusicModule.provideApplicationContext(mockApplication)

        assertThat(result).isEqualTo(mockApplication)
        verify { mockApplication.applicationContext }

        Instant.now()
    }

    class FakeMusicModule : MusicModule() {

    }
}
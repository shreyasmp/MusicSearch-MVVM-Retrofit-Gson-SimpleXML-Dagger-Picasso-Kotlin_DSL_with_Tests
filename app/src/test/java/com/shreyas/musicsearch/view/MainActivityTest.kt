package com.shreyas.musicsearch.view

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.shreyas.musicsearch.R
import com.shreyas.musicsearch.TestApplication
import com.shreyas.musicsearch.di.annotations.ViewModelKey
import com.shreyas.musicsearch.di.modules.PicassoModule
import com.shreyas.musicsearch.di.modules.ServiceModule
import com.shreyas.musicsearch.di.modules.ViewModelFactoryModule
import com.shreyas.musicsearch.runner.MusicRobolectricTestRunner
import com.shreyas.musicsearch.viewmodel.TrackLyricViewModel
import com.shreyas.musicsearch.viewmodel.TrackSearchViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import dagger.multibindings.IntoMap
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.annotation.LooperMode
import javax.inject.Singleton

@RunWith(MusicRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.LEGACY)
class MainActivityTest {

    private lateinit var activityTest: MainActivity

    @Before
    fun setUp() {
        activityTest =
            Robolectric.buildActivity(MainActivity::class.java).create().visible().get()
    }

    @Test
    fun `activity is successfully created`() {
        assertThat(activityTest).isNotNull()
    }

    @Test
    fun `should have correct app name`() {
        assertThat(activityTest.getString(R.string.app_name)).isEqualTo("MusicSearch-MVVM")
    }

    @Test
    fun `test activity view components are visible`() {
        assertThat(activityTest.binding.fragmentContainer.visibility).isEqualTo(View.VISIBLE)
        assertThat(activityTest.binding.mainLayout.visibility).isEqualTo(View.VISIBLE)
        assertThat(activityTest.binding.mainToolbar.visibility).isEqualTo(View.VISIBLE)
    }

    @Singleton
    @Component(modules = [TestAppModule::class])
    interface TestAppComponent {
        fun inject(testApplication: TestApplication)
    }

    @Module(
        includes = [
            AndroidSupportInjectionModule::class,
            AndroidInjectionModule::class,
            ViewModelFactoryModule::class,
            ServiceModule::class,
            PicassoModule::class,
            TestMusicViewModelModule::class
        ]
    )
    class TestAppModule {
        @Provides
        fun providesTestApplication(): Application =
            ApplicationProvider.getApplicationContext<TestApplication>()

        @Provides
        fun provideApplicationContext(application: Application): Context {
            return application.applicationContext
        }
    }

    @Module
    abstract class TestMusicViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(TrackSearchViewModel::class)
        abstract fun bindTrackSearchViewModel(trackSearchViewModel: TrackSearchViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(TrackLyricViewModel::class)
        abstract fun bindTrackLyricViewModel(trackLyricViewModel: TrackLyricViewModel): ViewModel
    }

    @After
    fun tearDown() {
        activityTest.finish()
    }
}
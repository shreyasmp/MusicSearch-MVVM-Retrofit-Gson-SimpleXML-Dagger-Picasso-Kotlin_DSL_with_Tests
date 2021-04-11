package com.shreyas.musicsearch.view

import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.common.truth.Truth.assertThat
import com.shreyas.musicsearch.model.TrackDetail
import com.shreyas.musicsearch.model.TrackSearchResults
import com.shreyas.musicsearch.runner.MusicRobolectricTestRunner
import com.shreyas.musicsearch.utils.TestJsonUtils.getObjectFromJsonFile
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import io.mockk.clearAllMocks
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.Shadows
import javax.inject.Inject

@RunWith(MusicRobolectricTestRunner::class)
class TrackListAdapterRoboTest {

    private val mockPicasso = mock(Picasso::class.java)
    private val mockRequestCreator = mock(RequestCreator::class.java)
    private var mockListener = mock(TrackListItemClickListener::class.java)

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var adapter: TrackListAdapter

    companion object {

        @BeforeClass
        @JvmStatic
        fun initMocks() {
            mockkStatic(
                Picasso::class
            )
        }

        @AfterClass
        @JvmStatic
        fun resetMocks() {
            unmockkStatic(
                Picasso::class
            )
        }
    }

    @Before
    fun setUp() {
        clearAllMocks()
        adapter = TrackListAdapter(mockPicasso)
        adapter.trackListItemClickListener = mockListener
        loadTrackSearchResults()
        MockitoAnnotations.openMocks(this)
        picasso = mockPicasso
        `when`(picasso.load(anyString())).thenReturn(mock(RequestCreator::class.java))
    }

    @Test
    fun `test the track search detail list item count is as expected`() {
        adapter.updateTrackSearchList(loadTrackSearchResults())
        assertThat(adapter.itemCount).isEqualTo(loadTrackSearchResults().size)
    }

    @Test
    fun `test if viewholder is not null`() {
        assertThat(createViewHolder()).isNotNull()
    }

    @Test
    fun `test if view holder is bind properly`() {
        val viewHolder = createViewHolder()
        adapter.updateTrackSearchList(loadTrackSearchResults())

        doReturn(mockRequestCreator).`when`(mockPicasso).load(anyString())
        doReturn(mockRequestCreator)
            .`when`(mockRequestCreator).placeholder(anyInt())
        doNothing().`when`(mockRequestCreator).into(viewHolder.binding.trackAlbumArt)

        adapter.onBindViewHolder(viewHolder, 0)
        viewHolder.bind(loadTrackSearchResults()[0])
        viewHolder.binding.artistName.text = loadTrackSearchResults()[0].artistName
        viewHolder.binding.trackName.text = loadTrackSearchResults()[0].trackName
        viewHolder.binding.trackAlbum.text = loadTrackSearchResults()[0].collectionName
        viewHolder.binding.trackDetailCard.performClick()
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        assertThat(viewHolder.binding.artistName.visibility).isEqualTo(View.VISIBLE)
        assertThat(viewHolder.binding.artistName.text).isEqualTo("Pink Floyd")

        assertThat(viewHolder.binding.trackName.visibility).isEqualTo(View.VISIBLE)
        assertThat(viewHolder.binding.trackName.text).isEqualTo("Comfortably Numb")

        assertThat(viewHolder.binding.trackAlbum.visibility).isEqualTo(View.VISIBLE)
        assertThat(viewHolder.binding.trackAlbum.text).isEqualTo("The Wall")

        verify(mockListener).onClickTrack(loadTrackSearchResults()[0])
    }

    private fun createViewHolder(): TrackListAdapter.TrackViewHolder {
        val linearLayout = LinearLayout(getApplicationContext())
        return adapter.onCreateViewHolder(linearLayout, 0)
    }

    private fun loadTrackSearchResults(): MutableList<TrackDetail> {
        val trackDetailList = mutableListOf<TrackDetail>()
        val response = getObjectFromJsonFile(
            jsonFile = "success_search_results.json",
            TrackSearchResults::class.java
        )
        if (response != null) {
            trackDetailList.addAll(response.results)
        }
        return trackDetailList
    }
}
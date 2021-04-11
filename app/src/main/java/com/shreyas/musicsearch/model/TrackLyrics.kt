package com.shreyas.musicsearch.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 *  The response from lyrics URL: http://api.chartlyrics.com/apiv1.asmx/
 *  is a SOAP XML and in order to handle the XML in Retrofit you either make
 *  use of SimpleXML converter or JAXB converter and set up the model data class
 *  in this format with @Root property and constructor with each field annotated
 *  with @field:Element() For the ones you may see in response as missing Tags
 *  mark them as required = false
 */
@Root(strict = false, name = "GetLyricResult")
data class TrackLyrics @JvmOverloads constructor(
    @field:Element(name = "TrackId", required = false)
    var trackID: Int = 0,
    @field:Element(name = "LyricChecksum", required = false)
    var checksum: String = "",
    @field:Element(name = "LyricId", required = false)
    var lyricID: Long = 0,
    @field:Element(name = "LyricSong", required = false)
    var songName: String = "",
    @field:Element(name = "LyricArtist", required = false)
    var artistName: String = "",
    @field:Element(name = "LyricUrl", required = false)
    var lyricURL: String = "",
    @field:Element(name = "LyricCovertArtUrl", required = false)
    var lyricsCoverArtURL: String = "",
    @field:Element(name = "LyricRank", required = false)
    var lyricRank: Long = 0,
    @field:Element(name = "LyricCorrectUrl", required = false)
    var lyricCorrectURL: String = "",
    @field:Element(name = "Lyric", required = false)
    var lyric: String = ""
)
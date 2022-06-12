package com.gmail.gerbencdg.eurosporttechtest.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsFeedDto(
    val videos: List<VideoPostDto>,
    val stories: List<StoryPostDto>
)

@JsonClass(generateAdapter = true)
data class StoryPostDto(
    val id: Int,
    val title: String,
    val teaser: String,
    val image: String,
    val date: Double,
    val author: String,
    val sport: SportDto,
)

@JsonClass(generateAdapter = true)
data class VideoPostDto(
    val id: Int,
    val title: String,
    val thumb: String,
    val url: String,
    val date: Double,
    val sport: SportDto,
    val views: Int
)

@JsonClass(generateAdapter = true)
@Json(name = "sport")
data class SportDto(
    val id: Int,
    val name: String
)



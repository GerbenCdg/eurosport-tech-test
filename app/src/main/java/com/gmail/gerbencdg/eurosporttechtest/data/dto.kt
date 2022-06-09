package com.gmail.gerbencdg.eurosporttechtest.data

import com.gmail.gerbencdg.eurosporttechtest.formatHumanReadableDate

data class SportDto(
    val id: Int,
    val name: String
)

abstract class NewsFeedPost {
    abstract val id: Int
    abstract val title: String
}

class StoryDto(
    override val id: Int,
    override val title: String,
    val teaser: String,
    val image: String,
    val date: Double,
    val author: String,
    val sport: SportDto
) : NewsFeedPost() {


}

class VideoDto(
    override val id: Int,
    override val title: String,
    val thumb: String,
    val url: String,
    val date: Double,
    val sport: SportDto,
    val views: Int
) : NewsFeedPost()



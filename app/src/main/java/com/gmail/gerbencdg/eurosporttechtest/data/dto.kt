package com.gmail.gerbencdg.eurosporttechtest.data

data class SportDto(
    val id: Int,
    val name: String
)

abstract class NewsFeedPost {
    abstract val id: Int
    abstract val title: String
    abstract var subtitle: String
    abstract val sport : SportDto
    abstract val imageUri : String
}

class StoryDto(
    override val id: Int,
    override val title: String,
    val teaser: String,
    val image: String,
    val date: Double,
    val author: String,
    override val sport: SportDto,
) : NewsFeedPost() {

    override lateinit var subtitle: String
    override val imageUri: String
        get() = image
}

class VideoDto(
    override val id: Int,
    override val title: String,
    val thumb: String,
    val url: String,
    val date: Double,
    override val sport: SportDto,
    val views: Int
) : NewsFeedPost() {

    override lateinit var subtitle: String
    override val imageUri: String
        get() = url
}



package com.gmail.gerbencdg.eurosporttechtest.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.gerbencdg.eurosporttechtest.api.StoryPostDto
import com.gmail.gerbencdg.eurosporttechtest.api.VideoPostDto


@Entity(tableName = "StoryPost")
data class StoryPostDb(
    @PrimaryKey val storyId: Int,
    val title: String,
    val teaser: String,
    val image: String,
    val date: Double,
    val author: String,
    val sport : String
)

@Entity(tableName = "VideoPost")
data class VideoPostDb(
    @PrimaryKey val videoId: Int,
    val title: String,
    val thumb: String,
    val url: String,
    val date: Double,
    val views: Int,
    val sport : String
)



fun StoryPostDto.asDatabaseModel() =
    StoryPostDb(
        storyId = id,
        title = title,
        teaser = teaser,
        image = image,
        date = date,
        author = author,
        sport = sport.name
    )

fun VideoPostDto.asDatabaseModel() =
    VideoPostDb(
        videoId = id,
        title = title,
        thumb = thumb,
        url = url,
        date = date,
        sport = sport.name,
        views = views
    )

fun List<StoryPostDto>.asDatabaseModel() = map { it.asDatabaseModel() }.toTypedArray()
fun List<VideoPostDto>.asDatabaseModel() = map { it.asDatabaseModel() }.toTypedArray()
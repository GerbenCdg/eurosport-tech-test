package com.gmail.gerbencdg.eurosporttechtest.domain

import android.content.Context
import com.gmail.gerbencdg.eurosporttechtest.data.NewsFeedDto
import com.gmail.gerbencdg.eurosporttechtest.data.SportDto
import com.gmail.gerbencdg.eurosporttechtest.data.StoryPostDto
import com.gmail.gerbencdg.eurosporttechtest.data.VideoPostDto
import org.joda.time.DateTime
import kotlin.math.roundToLong


fun StoryPostDto.toModel(context: Context): StoryPost {
    return StoryPost(
        id = this.id,
        title = this.title,
        subtitle = storyPostSubtitle(this.author, this.date, context),
        imageUri = this.image,
        sport = this.sport.toModel(),
        date = DateTime(this.date.roundToLong() * 1000),
        teaser = this.teaser,
        author = this.author
    )
}

fun VideoPostDto.toModel(context: Context): VideoPost {
    return VideoPost(
        id = this.id,
        title = this.title,
        subtitle = videoPostSubtitle(this.views, context),
        imageUri = this.thumb,
        sport = this.sport.toModel(),
        date = DateTime(this.date.roundToLong()),
        url = this.url,
        views = this.views
    )
}

fun NewsFeedDto.toModel(context: Context) : List<NewsFeedPost> {

    val newsFeed = mutableListOf<NewsFeedPost>()

    val stories = this.stories.map { it.toModel(context) }
    val videos = this.videos.map { it.toModel(context) }

    newsFeed.apply {
        addAll(stories)
        addAll(videos)
    }

    return newsFeed
}

fun SportDto.toModel(): Sport {
    return Sport(this.id, this.name)
}


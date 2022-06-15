package com.gmail.gerbencdg.eurosporttechtest.data

import com.gmail.gerbencdg.eurosporttechtest.data.source.local.StoryPostDb
import com.gmail.gerbencdg.eurosporttechtest.data.source.local.VideoPostDb
import com.gmail.gerbencdg.eurosporttechtest.api.NewsFeedDto
import com.gmail.gerbencdg.eurosporttechtest.api.SportDto
import com.gmail.gerbencdg.eurosporttechtest.api.StoryPostDto
import com.gmail.gerbencdg.eurosporttechtest.api.VideoPostDto
import com.gmail.gerbencdg.eurosporttechtest.domain.NewsFeedPost
import com.gmail.gerbencdg.eurosporttechtest.domain.Sport
import com.gmail.gerbencdg.eurosporttechtest.domain.StoryPost
import com.gmail.gerbencdg.eurosporttechtest.domain.VideoPost
import com.gmail.gerbencdg.eurosporttechtest.formatDate
import org.joda.time.DateTime
import kotlin.math.roundToLong


fun StoryPostDb.toModel(): StoryPost {
    return StoryPost(
        id = this.storyId,
        title = this.title,
        subtitle = "By $author – ${formatDate(date)}",
        imageUri = this.image,
        sport = Sport(this.sport),
        date = DateTime(this.date.roundToLong() * 1000),
        teaser = this.teaser,
        author = this.author
    )
}

fun VideoPostDb.toModel(): VideoPost {
    return VideoPost(
        id = this.videoId,
        title = this.title,
        subtitle = "$views views",
        imageUri = this.thumb,
        sport = Sport(this.sport),
        date = DateTime(this.date.roundToLong()),
        url = this.url,
        views = this.views
    )
}



fun StoryPostDto.toModel(): StoryPost {
    return StoryPost(
        id = this.id,
        title = this.title,
        subtitle = "By $author – ${formatDate(date)}",
        imageUri = this.image,
        sport = this.sport.toModel(),
        date = DateTime(this.date.roundToLong() * 1000),
        teaser = this.teaser,
        author = this.author
    )
}

fun VideoPostDto.toModel(): VideoPost {
    return VideoPost(
        id = this.id,
        title = this.title,
        subtitle = "$views views",
        imageUri = this.thumb,
        sport = this.sport.toModel(),
        date = DateTime(this.date.roundToLong()),
        url = this.url,
        views = this.views
    )
}

fun NewsFeedDto.toModel(): List<NewsFeedPost> =
    mutableListOf<NewsFeedPost>()
        .apply {
            addAll(stories.map { it.toModel() })
            addAll(videos.map { it.toModel() })
        }

fun SportDto.toModel(): Sport {
    return Sport(this.name)
}

fun StoryPost.toDbEntity(): StoryPostDb {
    return StoryPostDb(
        storyId = this.id,
        title = this.title,
        teaser = this.teaser,
        image = this.imageUri,
        date = (this.date.millis/1000).toDouble(),
        author = this.author,
        sport = this.sport.name
    )
}

fun VideoPost.toDbEntity(): VideoPostDb {
    return VideoPostDb(
        videoId = this.id,
        title = this.title,
        thumb = this.imageUri,
        url = this.url,
        date = (this.date.millis/1000).toDouble(),
        sport = this.sport.name,
        views = this.views
    )
}




package com.gmail.gerbencdg.eurosporttechtest.domain

import android.content.Context
import android.text.format.DateUtils
import com.gmail.gerbencdg.eurosporttechtest.R
import com.gmail.gerbencdg.eurosporttechtest.data.SportDto
import org.joda.time.DateTime
import kotlin.math.roundToLong


abstract class NewsFeedPost {
    abstract val id: Int
    abstract val title: String
    abstract val subtitle: String
    abstract val imageUri: String
    abstract val sport: Sport
    abstract val date: DateTime
}

data class StoryPost(
    override val id: Int,
    override val title: String,
    override val subtitle: String,
    override val imageUri: String,
    override val sport: Sport,
    override val date: DateTime,
    val teaser: String,
    val author: String,
) : NewsFeedPost()

data class VideoPost(
    override val id: Int,
    override val title: String,
    override val subtitle: String,
    override val imageUri: String,
    override val sport: Sport,
    override val date: DateTime,
    val url: String,
    val views: Int
) : NewsFeedPost()


data class Sport(
    val id: Int,
    val name: String
)


fun storyPostSubtitle(author: String, date: Double, context: Context): String {

    val formattedRelativeDateTime = DateUtils.getRelativeDateTimeString(
        context,
        date.roundToLong() * 1000,
        DateUtils.MINUTE_IN_MILLIS,
        DateUtils.YEAR_IN_MILLIS,
        0
    )

    return context.getString(R.string.newsfeed_story_subtitle, author, formattedRelativeDateTime)
}


fun videoPostSubtitle(views: Int, context: Context): String {

    return context.getString(R.string.newsfeed_video_subtitle, views)
}

fun SportDto.toModel(): Sport {
    return Sport(this.id, this.name)
}


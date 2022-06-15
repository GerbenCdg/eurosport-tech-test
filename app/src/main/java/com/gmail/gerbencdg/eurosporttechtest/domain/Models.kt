package com.gmail.gerbencdg.eurosporttechtest.domain

import android.content.Context
import android.os.Parcelable
import android.text.format.DateUtils
import com.gmail.gerbencdg.eurosporttechtest.R
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import kotlin.math.roundToLong


abstract class NewsFeedPost : Parcelable {
    abstract val id: Int
    abstract val title: String
    abstract val subtitle: String
    abstract val imageUri: String
    abstract val sport: Sport
    abstract val date: DateTime
}

@Parcelize
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

@Parcelize
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


@Parcelize
data class Sport(
    val name: String
) : Parcelable


package com.gmail.gerbencdg.eurosporttechtest.data

data class Sport(
    val id: Int,
    val name: String
)

data class Story(
    val id: Int,
    val title: String,
    val teaser: String,
    val image: String,
    val date: Double,
    val author: String,
    val sport: Sport
)

data class Video(
    val id: Int,
    val title: String,
    val thumb: String,
    val url: String,
    val date: Double,
    val sport: Sport,
    val views: Int
)
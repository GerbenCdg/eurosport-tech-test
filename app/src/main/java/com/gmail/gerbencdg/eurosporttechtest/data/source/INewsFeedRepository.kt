package com.gmail.gerbencdg.eurosporttechtest.data.source

import androidx.lifecycle.LiveData
import com.gmail.gerbencdg.eurosporttechtest.data.Result
import com.gmail.gerbencdg.eurosporttechtest.domain.NewsFeedPost

interface INewsFeedRepository {

    val postsObservable: LiveData<Result<List<NewsFeedPost>>>

    suspend fun refreshPosts()

    suspend fun getPosts(update : Boolean = false) : Result<List<NewsFeedPost>>
}
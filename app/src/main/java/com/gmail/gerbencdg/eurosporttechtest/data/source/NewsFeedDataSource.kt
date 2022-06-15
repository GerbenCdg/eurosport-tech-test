package com.gmail.gerbencdg.eurosporttechtest.data.source

import androidx.lifecycle.LiveData
import com.gmail.gerbencdg.eurosporttechtest.data.Result
import com.gmail.gerbencdg.eurosporttechtest.domain.NewsFeedPost

interface NewsFeedDataSource {

    fun observePosts() : LiveData<Result<List<NewsFeedPost>>>

    suspend fun getPosts() : Result<List<NewsFeedPost>>

    suspend fun refreshPosts()

    suspend fun deleteAllPosts()

    suspend fun savePosts(posts : List<NewsFeedPost>)
}
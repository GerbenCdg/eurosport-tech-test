package com.gmail.gerbencdg.eurosporttechtest.data.source

import androidx.lifecycle.LiveData
import com.gmail.gerbencdg.eurosporttechtest.data.NewsFeedPost
import com.gmail.gerbencdg.eurosporttechtest.data.Result

interface NewsFeedRepository {
    fun observePosts() : LiveData<Result<List<Result<NewsFeedPost>>>>

    suspend fun getPost(id: Int) : NewsFeedPost
}

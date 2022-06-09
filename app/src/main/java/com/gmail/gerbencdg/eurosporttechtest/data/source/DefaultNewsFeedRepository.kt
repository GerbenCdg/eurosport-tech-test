package com.gmail.gerbencdg.eurosporttechtest.data.source

import androidx.lifecycle.LiveData
import com.gmail.gerbencdg.eurosporttechtest.data.NewsFeedPost
import com.gmail.gerbencdg.eurosporttechtest.data.Result

class DefaultNewsFeedRepository : NewsFeedRepository {


    override fun observePosts(): LiveData<Result<List<Result<NewsFeedPost>>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(id: Int): NewsFeedPost {
        TODO("Not yet implemented")
    }

}
package com.gmail.gerbencdg.eurosporttechtest.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.gerbencdg.eurosporttechtest.data.Result
import com.gmail.gerbencdg.eurosporttechtest.domain.NewsFeedPost

class FakeNewsFeedRepository : INewsFeedRepository {

    var shouldReturnError = false

    var newsFeedData = linkedMapOf<Int,NewsFeedPost>()

    override val postsObservable = MutableLiveData<Result<List<NewsFeedPost>>>()

    override suspend fun refreshPosts() {
        postsObservable.postValue(getPosts())
    }

    override suspend fun getPosts(update: Boolean): Result<List<NewsFeedPost>> {
        if (shouldReturnError)
            return Result.Error(Exception("Unit Test Exception"))

        return Result.Success(newsFeedData.values.toList())
    }

}
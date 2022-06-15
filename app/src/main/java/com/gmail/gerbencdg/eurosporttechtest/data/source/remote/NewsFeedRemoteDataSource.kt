package com.gmail.gerbencdg.eurosporttechtest.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.gerbencdg.eurosporttechtest.api.NewsFeedApiService
import com.gmail.gerbencdg.eurosporttechtest.data.Result
import com.gmail.gerbencdg.eurosporttechtest.data.source.NewsFeedDataSource
import com.gmail.gerbencdg.eurosporttechtest.domain.NewsFeedPost
import com.gmail.gerbencdg.eurosporttechtest.data.toModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsFeedRemoteDataSource @Inject constructor(
    private val newsFeedApi: NewsFeedApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsFeedDataSource {

    private val _observablePosts = MutableLiveData<Result<List<NewsFeedPost>>>()

    override fun observePosts(): LiveData<Result<List<NewsFeedPost>>> {
        return _observablePosts
    }

    override suspend fun getPosts(): Result<List<NewsFeedPost>> {
        return withContext(ioDispatcher) {
            try {
                val newsFeedDto = newsFeedApi.getNewsFeed()
                Result.Success(newsFeedDto.toModel())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun refreshPosts() {
        _observablePosts.postValue(getPosts())
    }

    override suspend fun deleteAllPosts() {
        throw NotImplementedError()
    }

    override suspend fun savePosts(posts: List<NewsFeedPost>) {
        throw NotImplementedError()
    }
}
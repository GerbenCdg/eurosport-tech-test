package com.gmail.gerbencdg.eurosporttechtest.data.source

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.gerbencdg.eurosporttechtest.api.NewsFeedApiService
import com.gmail.gerbencdg.eurosporttechtest.data.Result
import com.gmail.gerbencdg.eurosporttechtest.domain.NewsFeedPost
import com.gmail.gerbencdg.eurosporttechtest.domain.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsFeedRepository @Inject constructor(
    private val newsFeedApi: NewsFeedApiService
) {

    private val _posts = MutableLiveData<Result<List<NewsFeedPost>>>()

    val posts: LiveData<Result<List<NewsFeedPost>>>
        get() = _posts

    suspend fun fetchPosts(context: Context) = withContext(Dispatchers.IO) {

        try {
            _posts.postValue(Result.Loading)

            val newsFeedDto = newsFeedApi.getNewsFeed()
            val newsFeedPosts = newsFeedDto.toModel(context)
                .sortedByDescending { it.date };

            _posts.postValue(Result.Success(newsFeedPosts));
        } catch (e: Exception) {
            _posts.postValue(Result.Error(e))
        }
    }

    fun getPost(id: Int): NewsFeedPost? {
        return (_posts.value as? Result.Success)?.data?.find { it.id == id }
    }


}
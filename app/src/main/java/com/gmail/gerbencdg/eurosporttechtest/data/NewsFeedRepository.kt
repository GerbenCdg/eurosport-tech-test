package com.gmail.gerbencdg.eurosporttechtest.data

import androidx.lifecycle.*
import com.gmail.gerbencdg.eurosporttechtest.data.source.NewsFeedDataSource
import com.gmail.gerbencdg.eurosporttechtest.domain.NewsFeedPost
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
/**
 * The [NewsFeedRepository] enables synchronization between a local [NewsFeedDataSource] and a remote [NewsFeedDataSource]
 */
class NewsFeedRepository @Inject constructor(
    @Named("NewsFeedLocalDataSource") private val newsFeedLocal: NewsFeedDataSource,
    @Named("NewsFeedRemoteDataSource") private val newsFeedRemote : NewsFeedDataSource,
) {

    val postsObservable: LiveData<Result<List<NewsFeedPost>>>
        get() = newsFeedLocal.observePosts()

    suspend fun refreshPosts() {
        updatePostsFromRemoteDataSource()
    }

    suspend fun getPosts(update : Boolean = false) : Result<List<NewsFeedPost>> {

        if (update) {
            try {
                updatePostsFromRemoteDataSource()
            } catch (e: Exception) {
                return Result.Error(e)
            }
        }
        return newsFeedLocal.getPosts()
    }

    private suspend fun updatePostsFromRemoteDataSource() {
        val remotePosts = newsFeedRemote.getPosts()

        if (remotePosts is Result.Success) {
            newsFeedLocal.deleteAllPosts()
            newsFeedLocal.savePosts(remotePosts.data)
        } else if (remotePosts is Result.Error) {
            throw remotePosts.exception
        }
    }

}
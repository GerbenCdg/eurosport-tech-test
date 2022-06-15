package com.gmail.gerbencdg.eurosporttechtest.data.source

import androidx.lifecycle.*
import com.gmail.gerbencdg.eurosporttechtest.data.Result
import com.gmail.gerbencdg.eurosporttechtest.domain.NewsFeedPost
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


/**
 * The [NewsFeedRepository] enables synchronization between a local [NewsFeedDataSource] and a remote [NewsFeedDataSource]
 */
class NewsFeedRepository @Inject constructor(
    @Named("NewsFeedLocalDataSource") private val newsFeedLocal: NewsFeedDataSource,
    @Named("NewsFeedRemoteDataSource") private val newsFeedRemote : NewsFeedDataSource,
) : INewsFeedRepository {

    override val postsObservable: LiveData<Result<List<NewsFeedPost>>>
        get() = newsFeedLocal.observePosts()

    override suspend fun refreshPosts() {
        updatePostsFromRemoteDataSource()
    }

    override suspend fun getPosts(update : Boolean) : Result<List<NewsFeedPost>> {

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
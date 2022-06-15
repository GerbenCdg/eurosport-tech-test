package com.gmail.gerbencdg.eurosporttechtest.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.gmail.gerbencdg.eurosporttechtest.data.Result
import com.gmail.gerbencdg.eurosporttechtest.data.source.NewsFeedDataSource
import com.gmail.gerbencdg.eurosporttechtest.data.toDbEntity
import com.gmail.gerbencdg.eurosporttechtest.data.toModel
import com.gmail.gerbencdg.eurosporttechtest.domain.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class NewsFeedLocalDataSource @Inject constructor(
    private val newsFeedDatabase: NewsFeedDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsFeedDataSource {

    private val newsFeedDao = newsFeedDatabase.newsFeedDao

    private val storiesDbObservable = newsFeedDao.observeStoryPosts()
    private val videosDbObservable = newsFeedDao.observeVideoPosts()

    private val storiesDb: List<StoryPostDb> by lazy { newsFeedDao.getStoryPosts() }
    private val videosDb: List<VideoPostDb> by lazy { newsFeedDao.getVideoPosts() }

    override fun observePosts(): LiveData<Result<List<NewsFeedPost>>> {

        val storyPostsObservable = storiesDbObservable.map { posts -> posts.map { it.toModel() } }
        val videoPostsObservable = videosDbObservable.map { posts -> posts.map { it.toModel() } }

        val posts = mutableListOf<NewsFeedPost>()

        val mediatorLiveData = MediatorLiveData<Result<List<NewsFeedPost>>>()
            .apply {
                addSource(storyPostsObservable) {
                    posts.apply {
                        removeAll(filterIsInstance<StoryPost>())
                        addAll(it)
                    }
                    postValue(Result.Success(posts))
                }
                addSource(videoPostsObservable) {
                    posts.apply {
                        removeAll(filterIsInstance<VideoPost>())
                        addAll(it)
                    }
                    postValue(Result.Success(posts))
                }
            }

        return mediatorLiveData
    }

    override suspend fun getPosts(): Result<List<NewsFeedPost>> {
        return withContext(ioDispatcher) {
            return@withContext try {
                val storyPosts = storiesDb.map { it.toModel() }
                val videoPosts = videosDb.map { it.toModel() }

                val posts = mutableListOf<NewsFeedPost>()
                    .apply {
                        addAll(storyPosts)
                        addAll(videoPosts)
                    }

                Result.Success(posts)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    // Not applicable
    override suspend fun refreshPosts() {
    }

    override suspend fun deleteAllPosts() {
        withContext(ioDispatcher) {
            newsFeedDao.deleteStoryPosts()
            newsFeedDao.deleteVideoPosts()
        }
    }

    override suspend fun savePosts(posts: List<NewsFeedPost>) {
        withContext(ioDispatcher) {
            val storiesDb = posts.filterIsInstance<StoryPost>()
                .map { it.toDbEntity() }
                .toTypedArray()

            val videosDb = posts.filterIsInstance<VideoPost>()
                .map { it.toDbEntity() }
                .toTypedArray()

            newsFeedDao.insertAll(*storiesDb)
            newsFeedDao.insertAll(*videosDb)
        }
    }
}
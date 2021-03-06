package com.gmail.gerbencdg.eurosporttechtest.newsfeed

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.gmail.gerbencdg.eurosporttechtest.Event
import com.gmail.gerbencdg.eurosporttechtest.R
import com.gmail.gerbencdg.eurosporttechtest.data.Result
import com.gmail.gerbencdg.eurosporttechtest.data.Result.Success
import com.gmail.gerbencdg.eurosporttechtest.data.source.INewsFeedRepository
import com.gmail.gerbencdg.eurosporttechtest.domain.NewsFeedPost
import com.gmail.gerbencdg.eurosporttechtest.domain.StoryPost
import com.gmail.gerbencdg.eurosporttechtest.domain.VideoPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsFeedRepository: INewsFeedRepository,
) : ViewModel() {

    private val _posts: LiveData<List<NewsFeedPost>> =
        newsFeedRepository.postsObservable.switchMap { filterPosts(it) }

    val posts: LiveData<List<NewsFeedPost>>
        get() = _posts

    private val _isDataLoadingError = MutableLiveData<Boolean>()
    val isDataLoadingError: LiveData<Boolean>
        get() = _isDataLoadingError

    private val _isDataLoading = MutableLiveData<Boolean>()

    val isDataLoading: LiveData<Boolean>
        get() = _isDataLoading

    private val _snackbarText = MutableLiveData<Event<Int>>()

    val snackbarText: LiveData<Event<Int>>
        get() = _snackbarText

    private val _navigate = MutableLiveData<Event<NewsFeedPost>>()

    val navigate: LiveData<Event<NewsFeedPost>>
        get() = _navigate


    init {
        refreshPosts()
    }

    fun onNewsPostClick(post: NewsFeedPost) {
        _navigate.postValue(Event(post))
    }

    fun refreshPosts() {

        _isDataLoading.postValue(true)

        viewModelScope.launch {
            newsFeedRepository.refreshPosts()
            _isDataLoading.postValue(false)
        }
    }

    private fun filterPosts(postsResult: Result<List<NewsFeedPost>>): LiveData<List<NewsFeedPost>> {

        val result = MutableLiveData<List<NewsFeedPost>>()

        when (postsResult) {
            is Success -> {
                viewModelScope.launch {

                    val storyPosts = postsResult.data.filterIsInstance<StoryPost>()
                    val videoPosts = postsResult.data.filterIsInstance<VideoPost>()

                    if (storyPosts.isEmpty() || videoPosts.isEmpty()) {
                        result.postValue(storyPosts.ifEmpty { videoPosts })
                        return@launch
                    }
                    val posts: List<NewsFeedPost> =
                        getViewModelPosts(storyPosts, videoPosts)

                    result.postValue(posts)
                }
            }
            is Result.Error -> {
                result.value = emptyList()
                showSnackbarMessage(R.string.error_loading_posts)

                _isDataLoadingError.postValue(true)
            }
        }

        return result
    }

    public fun resetDataLoadingError() {
        _isDataLoadingError.postValue(false)
    }

    /**
     * Fill the list of posts with story posts or video posts
     * In order to equalize the number of posts in both categories
     */
    private fun getViewModelPosts(
        storyPosts: List<StoryPost>,
        videoPosts: List<VideoPost>,
    ): List<NewsFeedPost> {

        val moreStoryPostsThanVideoPosts = storyPosts.count() > videoPosts.count()
        val mostRepresentedPostCategoryCount =
            max(storyPosts.count(), videoPosts.count())


        val fillPostsSequence = sequence {
            for (i in 0..mostRepresentedPostCategoryCount) {
                if (moreStoryPostsThanVideoPosts) {
                    yield(videoPosts[i % videoPosts.count()])
                } else {
                    yield(storyPosts[i % storyPosts.count()])
                }
            }
        }

        val posts: List<NewsFeedPost> =
            if (moreStoryPostsThanVideoPosts) {
                storyPosts
            } else {
                videoPosts
            }.zip(fillPostsSequence.take(mostRepresentedPostCategoryCount)
                .toMutableList())
                .flatMap { it.toList() }
        return posts
    }

    private fun showSnackbarMessage(@StringRes resId: Int) {
        _snackbarText.postValue(Event(resId))
    }
}
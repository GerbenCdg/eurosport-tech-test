package com.gmail.gerbencdg.eurosporttechtest.newsfeed

import android.app.Application
import androidx.lifecycle.*
import com.gmail.gerbencdg.eurosporttechtest.Event
import com.gmail.gerbencdg.eurosporttechtest.R
import com.gmail.gerbencdg.eurosporttechtest.data.Result
import com.gmail.gerbencdg.eurosporttechtest.data.Result.Success
import com.gmail.gerbencdg.eurosporttechtest.data.source.NewsFeedRepository
import com.gmail.gerbencdg.eurosporttechtest.domain.NewsFeedPost
import com.gmail.gerbencdg.eurosporttechtest.domain.StoryPost
import com.gmail.gerbencdg.eurosporttechtest.domain.VideoPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val _app: Application,
    private val _newsFeedRepository: NewsFeedRepository
) : AndroidViewModel(_app) {

    private val _posts: LiveData<List<NewsFeedPost>> =
        _newsFeedRepository.posts.switchMap { filterPosts(it) }

    val posts: LiveData<List<NewsFeedPost>>
        get() = _posts

    private val _isDataLoadingError = MutableLiveData<Boolean>()
    private val isDataLoadingError: LiveData<Boolean>
        get() = _isDataLoadingError
    
    private val _isDataLoading = MutableLiveData<Boolean>()
    
    val isDataLoading : LiveData<Boolean>
    get() = _isDataLoading
    
    private val _snackbarText = MutableLiveData<Event<String>>()
    
    val snackbarText : LiveData<Event<String>>
    get() = _snackbarText


    init {
        viewModelScope.launch {
            _newsFeedRepository.fetchPosts(_app)
        }
    }

    fun onPostClick(post: NewsFeedPost) {

    }

    private fun filterPosts(postsResult: Result<List<NewsFeedPost>>): LiveData<List<NewsFeedPost>> {

        val result = MutableLiveData<List<NewsFeedPost>>()

        when (postsResult) {
            is Success -> {
                _isDataLoadingError.postValue(false)
                viewModelScope.launch {

                    val orderedPosts = postsResult.data.filterIsInstance<StoryPost>()
                        .zip(postsResult.data.filterIsInstance<VideoPost>())
                        .flatMap { it.toList() }

                    result.postValue(orderedPosts)

                    _isDataLoading.postValue(false)
                }
            }
            is Result.Loading -> {
                _isDataLoading.postValue(true)
            }
            is Result.Error -> {
                result.value = emptyList()
                showSnackbarMessage(_app.getString(R.string.error_loading_posts))

                _isDataLoading.postValue(false)
                _isDataLoadingError.postValue(true)
            }
        }

        return result
    }

    private fun showSnackbarMessage(msg: String) {
        _snackbarText.postValue(Event(msg))
    }
}
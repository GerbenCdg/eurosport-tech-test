package com.gmail.gerbencdg.eurosporttechtest.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gmail.gerbencdg.eurosporttechtest.Event
import com.gmail.gerbencdg.eurosporttechtest.MainCoroutineRule
import com.gmail.gerbencdg.eurosporttechtest.data.source.FakeNewsFeedRepository
import com.gmail.gerbencdg.eurosporttechtest.domain.Sport
import com.gmail.gerbencdg.eurosporttechtest.domain.StoryPost
import com.gmail.gerbencdg.eurosporttechtest.domain.VideoPost
import com.gmail.gerbencdg.eurosporttechtest.getOrAwaitValue
import com.gmail.gerbencdg.eurosporttechtest.newsfeed.NewsFeedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsFeedViewModelTest {

    private lateinit var viewModel: NewsFeedViewModel

    private lateinit var newsFeedRepository: FakeNewsFeedRepository

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Setup TestCoroutineDispatcher as Dispatchers.Main, which is the dispatcher used by ViewModelScope.launch { ... }
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        newsFeedRepository = FakeNewsFeedRepository()

        val storyPost1 = StoryPost(0, "storyPost1", "testStorySubtitle1", "https://yourapi.com/2234", Sport("Football"), DateTime.now(), "teaser1", "author1")
        val storyPost2 = StoryPost(1, "storyPost2", "testStorySubtitle2", "https://yourapi.com/2234", Sport("Rugby"), DateTime.now(), "teaser2", "author2")
        val videoPost = VideoPost(2, "videoPost1", "testVideoSubtitle1", "https://yourapi.com/2234", Sport("Football"), DateTime.now(), "teaser3", 42069)
        val videoPost2 = VideoPost(3, "videoPost2", "testVideoSubtitle2", "https://yourapi.com/2234", Sport("Rugby"), DateTime.now(), "teaser4", 42069)

        newsFeedRepository.newsFeedData[storyPost1.id] = storyPost1
        newsFeedRepository.newsFeedData[storyPost2.id] = storyPost2
        newsFeedRepository.newsFeedData[videoPost.id] = videoPost
        newsFeedRepository.newsFeedData[videoPost2.id] = videoPost2

        viewModel = NewsFeedViewModel(newsFeedRepository)
    }


    @Test
    fun refreshPosts_setsIsDataLoading() {
        // Arrange
        mainCoroutineRule.pauseDispatcher()

        // Act
        viewModel.refreshPosts()

        // Assert
        assertThat(viewModel.isDataLoading.getOrAwaitValue(), `is`(true))
        mainCoroutineRule.resumeDispatcher()

        assertThat(viewModel.isDataLoading.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun refreshPosts_withError_setsIsDataLoadingError() {
        // Arrange
        newsFeedRepository.shouldReturnError = true
        // We need to observe the posts LiveData in order to trigger isDataLoadingError
        viewModel.posts.observeForever{}

        // Act
         viewModel.refreshPosts()

        // Assert
        assertThat(viewModel.isDataLoadingError.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun refreshPosts_withError_resetDataLoadingError() {
        // Arrange
        newsFeedRepository.shouldReturnError = true
        // We need to observe the posts LiveData in order to trigger isDataLoadingError
        viewModel.posts.observeForever{}

        // Act
        viewModel.refreshPosts()
        viewModel.resetDataLoadingError()

        // Assert
        assertThat(viewModel.isDataLoadingError.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun onNewsPostClick_setsNavigateObservable() {

        // Arrange
        val post = newsFeedRepository.newsFeedData[0]!!

        // Act
        viewModel.onNewsPostClick(post)

        // Assert
        assertThat(viewModel.navigate.getOrAwaitValue().peekContent(), `is`(post))
    }





}




























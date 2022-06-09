package com.gmail.gerbencdg.eurosporttechtest.newsfeed

import androidx.lifecycle.ViewModel
import com.gmail.gerbencdg.eurosporttechtest.data.source.NewsFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsFeedRepository : NewsFeedRepository
): ViewModel() {
}
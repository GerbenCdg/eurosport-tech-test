package com.gmail.gerbencdg.eurosporttechtest.newsfeed

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class NewsFeedFragment : Fragment(){

    private val viewModel : NewsFeedViewModel by viewModels()

    private lateinit var listAdapter: NewsFeedAdapter

}
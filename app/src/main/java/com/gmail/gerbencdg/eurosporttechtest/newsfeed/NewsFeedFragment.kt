package com.gmail.gerbencdg.eurosporttechtest.newsfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.gmail.gerbencdg.eurosporttechtest.R
import com.gmail.gerbencdg.eurosporttechtest.databinding.FragmentNewsfeedBinding
import com.gmail.gerbencdg.eurosporttechtest.domain.StoryPost
import com.gmail.gerbencdg.eurosporttechtest.domain.VideoPost
import com.gmail.gerbencdg.eurosporttechtest.showSnackbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_newsfeed.*

@AndroidEntryPoint
class NewsFeedFragment : Fragment() {

    private val viewModel: NewsFeedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewsfeedBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.newsfeedRecycler.adapter = NewsFeedAdapter(viewModel)

        viewModel.navigate.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    is VideoPost -> findNavController()
                        .navigate(NewsFeedFragmentDirections.actionNewsFeedFragmentToVideoPlayerFragment(it))
                    is StoryPost -> findNavController()
                        .navigate(NewsFeedFragmentDirections.actionNewsFeedFragmentToStoryFragment(it))
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSnackbar()
    }


    private fun setupSnackbar() {
        viewModel.snackbarText.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                view?.showSnackbar(it, Snackbar.LENGTH_LONG)
            }
        }
    }

}
package com.gmail.gerbencdg.eurosporttechtest.story

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gmail.gerbencdg.eurosporttechtest.R
import com.gmail.gerbencdg.eurosporttechtest.databinding.FragmentStoryBinding
import com.gmail.gerbencdg.eurosporttechtest.showSnackbar
import com.gmail.gerbencdg.eurosporttechtest.videoplayer.VideoPlayerFragmentArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_story.*

@AndroidEntryPoint
class StoryFragment : Fragment() {

    private val args: StoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentStoryBinding.inflate(inflater)
        binding.story = args.selectedStory

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_item_share) {
                // TODO handle share
                view.showSnackbar("Share action here", Snackbar.LENGTH_SHORT)
                return@setOnMenuItemClickListener true
            }

            return@setOnMenuItemClickListener false
        }

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        toolbar.inflateMenu(R.menu.menu_story)
    }

}
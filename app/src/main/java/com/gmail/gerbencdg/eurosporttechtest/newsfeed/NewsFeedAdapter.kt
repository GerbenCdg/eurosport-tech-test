package com.gmail.gerbencdg.eurosporttechtest.newsfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.gerbencdg.eurosporttechtest.data.NewsFeedPost
import com.gmail.gerbencdg.eurosporttechtest.databinding.NewsfeedpostItemBinding
import com.gmail.gerbencdg.eurosporttechtest.newsfeed.NewsFeedAdapter.ViewHolder

class NewsFeedAdapter(private val viewModel: NewsFeedViewModel) :
ListAdapter<NewsFeedPost, ViewHolder>(TaskDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: NewsfeedpostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NewsFeedViewModel, item: NewsFeedPost) {

            binding.viewModel = viewModel
            binding.post = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsfeedpostItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}



class TaskDiffCallback : DiffUtil.ItemCallback<NewsFeedPost>() {

    override fun areItemsTheSame(oldItem: NewsFeedPost, newItem: NewsFeedPost): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsFeedPost, newItem: NewsFeedPost): Boolean {
        return areItemsTheSame(oldItem,newItem)
    }
}

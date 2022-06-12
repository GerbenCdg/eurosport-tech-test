package com.gmail.gerbencdg.eurosporttechtest

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.gerbencdg.eurosporttechtest.domain.NewsFeedPost
import com.gmail.gerbencdg.eurosporttechtest.domain.VideoPost
import com.gmail.gerbencdg.eurosporttechtest.newsfeed.NewsFeedAdapter


/**
 * [BindingAdapter] to set an image from a url using Glide
 */
@BindingAdapter("imageUri")
fun bindImage(imageView: ImageView, url: String) {

    Glide.with(imageView)
        .load(url)
        .placeholder(R.drawable.placeholder_picture_of_day)
        .centerCrop()
        .into(imageView)

}

/**
 * [BindingAdapter] to change the visibility of the "play" icon
 */
@BindingAdapter("playIconVisibility")
fun bindPlayIconImageVisibility(imageView: ImageView, post: NewsFeedPost) {

    if (post is VideoPost) {
        imageView.visibility = View.VISIBLE
    } else {
        imageView.visibility = View.GONE
    }
}

/**
 * [BindingAdapter] to set the [NewsFeedPost]s
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<NewsFeedPost>?) {
    items?.let {
        (listView.adapter as NewsFeedAdapter).submitList(items)
    }
}
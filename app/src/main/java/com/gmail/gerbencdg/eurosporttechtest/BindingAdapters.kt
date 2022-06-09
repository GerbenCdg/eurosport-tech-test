package com.gmail.gerbencdg.eurosporttechtest

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.gmail.gerbencdg.eurosporttechtest.data.NewsFeedPost
import com.gmail.gerbencdg.eurosporttechtest.data.VideoDto

@BindingAdapter("imageUri")
fun bindImage(imageView: ImageView, url: String, contentDescription: String) {

    Glide.with(imageView)
        .load(url)
        .placeholder(R.drawable.placeholder_picture_of_day)
        .into(imageView)

    imageView.contentDescription = contentDescription
}

@BindingAdapter("playIconVisibility")
fun bindPlayIconImageVisibility(imageView: ImageView, post: NewsFeedPost) {

    if (post is VideoDto) {
        imageView.visibility = View.VISIBLE
    } else {
        imageView.visibility = View.GONE
    }
}
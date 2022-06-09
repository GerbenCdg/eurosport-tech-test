package com.gmail.gerbencdg.eurosporttechtest

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUri")
fun bindImage(imageView: ImageView, url: String, contentDescription: String) {

    Glide.with(imageView)
        .load(url)
        .placeholder(R.drawable.placeholder_picture_of_day)
        .into(imageView)

    imageView.contentDescription = contentDescription
}

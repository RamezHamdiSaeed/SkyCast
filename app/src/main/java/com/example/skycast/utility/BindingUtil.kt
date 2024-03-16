package com.example.skycast.utility

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.skycast.R
import com.squareup.picasso.Picasso

@BindingAdapter("myImageUrl")
fun loadImage(imageView: ImageView,url:String?){
    url?.let {
        if (it.isNotEmpty()) {
            Glide.with(imageView.context)
                .load(it)
                .into(imageView)
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_foreground) // Default image from resources
        }
    } ?: run {
        imageView.setImageResource(R.drawable.ic_launcher_foreground) // Default image from resources
    }

}

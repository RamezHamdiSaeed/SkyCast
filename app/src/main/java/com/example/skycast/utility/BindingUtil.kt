package com.example.skycast.utility

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.skycast.R
import com.squareup.picasso.Picasso

@BindingAdapter("myImageUrl")
fun loadImage(imageView: ImageView,url:String){
    Picasso.get().load(url).error(R.drawable.ic_launcher_foreground).into(imageView)
}

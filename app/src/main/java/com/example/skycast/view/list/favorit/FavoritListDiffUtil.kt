package com.example.skycast.view.list.favorit

import androidx.recyclerview.widget.DiffUtil
import com.example.skycast.view.list.model.WeatherInfo

class FavoritListDiffUtil :DiffUtil.ItemCallback<WeatherInfo>() {
    override fun areItemsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean {
        return oldItem==newItem
    }

}
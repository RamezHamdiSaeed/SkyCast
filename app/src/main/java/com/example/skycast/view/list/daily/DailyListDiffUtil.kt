package com.example.skycast.view.list.daily

import androidx.recyclerview.widget.DiffUtil
import com.example.skycast.view.list.model.WeatherBriefInfo

class DailyListDiffUtil :DiffUtil.ItemCallback<WeatherBriefInfo>() {
    override fun areItemsTheSame(oldItem: WeatherBriefInfo, newItem: WeatherBriefInfo): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: WeatherBriefInfo, newItem: WeatherBriefInfo): Boolean {
        return oldItem==newItem
    }
}
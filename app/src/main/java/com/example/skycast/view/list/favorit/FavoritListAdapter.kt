package com.example.skycast.view.list.favorit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skycast.databinding.CardFavoriteCardItemBinding
import com.example.skycast.databinding.CardHourlyCardItemBinding
import com.example.skycast.view.list.model.WeatherBriefInfo
import com.example.skycast.view.list.model.WeatherInfo

class FavoritListAdapter() :ListAdapter<WeatherInfo,FavoritListAdapter.ViewHolder>(FavoritListDiffUtil()){
    private lateinit var binding:CardFavoriteCardItemBinding
   inner class ViewHolder(var binding: CardFavoriteCardItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater:LayoutInflater=LayoutInflater.from(parent.context)
        binding= CardFavoriteCardItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.binding.item=item

    }
}
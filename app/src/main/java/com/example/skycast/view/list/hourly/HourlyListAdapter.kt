package com.example.skycast.view.list.hourly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skycast.R
import com.example.skycast.databinding.CardHourlyCardItemBinding
import com.example.skycast.view.list.model.WeatherBriefInfo

class HourlyListAdapter() :ListAdapter<WeatherBriefInfo,HourlyListAdapter.ViewHolder>(HourlyListDiffUtil()){
    private lateinit var binding:CardHourlyCardItemBinding
   inner class ViewHolder(var binding:CardHourlyCardItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater:LayoutInflater=LayoutInflater.from(parent.context)
        binding= CardHourlyCardItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.binding.tvItemHour.text=item.time
        holder.binding.tvItemTemp.text=item.temp
        Glide.with(holder.itemView).load(item.icon).into(holder.binding.imgItemIcon)
    }
}
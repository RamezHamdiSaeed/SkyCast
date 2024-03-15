package com.example.skycast.view.list.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skycast.databinding.CardDailyCardItemBinding
import com.example.skycast.databinding.CardHourlyCardItemBinding
import com.example.skycast.view.list.model.WeatherBriefInfo

class DailyListAdapter() :ListAdapter<WeatherBriefInfo,DailyListAdapter.ViewHolder>(DailyListDiffUtil()){
    private lateinit var binding:CardDailyCardItemBinding
   inner class ViewHolder(var binding:CardDailyCardItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater:LayoutInflater=LayoutInflater.from(parent.context)
        binding= CardDailyCardItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.binding.item=item
    }
}
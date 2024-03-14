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
import com.example.skycast.view.list.model.WeatherBriefInfo

class HourlyListAdapter() :ListAdapter<WeatherBriefInfo,HourlyListAdapter.ViewHolder>(HourlyListDiffUtil()){
   inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val time:TextView=itemView.findViewById(R.id.tvItemHour)
        val icon:ImageView=itemView.findViewById(R.id.imgItemIcon)
        val temp:TextView=itemView.findViewById(R.id.tvItemTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.card_hourly_card_item,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.time.text=item.time
        holder.temp.text=item.temp
        Glide.with(holder.itemView).load(item.icon).into(holder.icon)
    }
}
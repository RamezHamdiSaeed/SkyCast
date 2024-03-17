package com.example.skycast.view.list.favorit

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skycast.databinding.CardFavoriteCardItemBinding
import com.example.skycast.model.WeatherInfo

class FavoritListAdapter(
    var closeButtonOnClick:(weatherInfo:WeatherInfo)->Unit) :ListAdapter<WeatherInfo,FavoritListAdapter.ViewHolder>(FavoritListDiffUtil()){
    private lateinit var binding:CardFavoriteCardItemBinding
    private  val TAG:String="FavoriteListAdapter"
   inner class ViewHolder(var binding: CardFavoriteCardItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater:LayoutInflater=LayoutInflater.from(parent.context)
        binding= CardFavoriteCardItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.binding.item=item
        holder.binding.imgItemClose.setOnClickListener{
            closeButtonOnClick(item)
        }
        Log.d(TAG, "onBindViewHolder: ${item.longitude}")

    }

}
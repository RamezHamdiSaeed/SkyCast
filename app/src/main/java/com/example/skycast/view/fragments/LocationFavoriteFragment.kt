package com.example.skycast.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skycast.R
import com.example.skycast.databinding.FragmentLocationFavoriteBinding
import com.example.skycast.view.list.daily.DailyListAdapter
import com.example.skycast.view.list.favorit.FavoritListAdapter
import com.example.skycast.view.list.model.WeatherInfo

class LocationFavoriteFragment : Fragment() {

private val TAG="LocationFavoriteFragment"
    private lateinit var binding:FragmentLocationFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        binding=FragmentLocationFavoriteBinding.inflate(inflater, container, false)
        binding.favoriteList.layoutManager= LinearLayoutManager(requireActivity())

        val favoritListAdapter= FavoritListAdapter()
        binding.favoriteList.adapter=favoritListAdapter

        binding.PbFavorit.visibility=View.GONE
        binding.favoriteList.visibility=View.VISIBLE

        favoritListAdapter.submitList(listOf(WeatherInfo("zagazig","12.121212","13.22","23","https://openweathermap.org/img/wn/10d@2x.png","good weather"),
            WeatherInfo("zagazig","12.121212","13.22","23","https://openweathermap.org/img/wn/10d@2x.png","good weather"),
            WeatherInfo("rhyad","12.121212","13.22","23","https://openweathermap.org/img/wn/10d@2x.png","good weather"),
            WeatherInfo("menya el kam7","12.121212","13.22","23","https://openweathermap.org/img/wn/10d@2x.png","good weather")))


        return binding.root
    }

}
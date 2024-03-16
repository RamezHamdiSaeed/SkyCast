package com.example.skycast.view.list.favorit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skycast.databinding.CardFavoriteCardItemBinding
import com.example.skycast.model.Weather
import com.example.skycast.model.WeatherInfo
import com.example.skycast.utility.DataManipulator
import com.example.skycast.utility.Status
import com.example.skycast.viewModel.MyViewModelSingleton
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KFunction5

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
//        val connectivityManager: ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
//        if (networkInfo != null && networkInfo.isConnected) {
//            MyViewModelSingleton.sharedViewModel.getCurrentWeatherConditionsAPI(item.lat,item.long)
//            handleCrudOperation(
//                MyViewModelSingleton.sharedViewModel.currentWeatherConditions,
//                { info ->
//                    val data: Weather = info as Weather
//                    val dataManipulator= DataManipulator(context)
//                        item.temp=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Temp,data?.current?.temp.toString())
//                        item.icon=dataManipulator.prepareImageUrl(data?.current?.weather?.get(0)?.icon?:"")
//                        item.description=data?.current?.weather?.get(0)?.description?:""
////                    MyViewModelSingleton.sharedViewModel.insertLocation(item)
//                },
//                {
//                },
//                {
//                },
//                "currentWeatherConditions"
//            )
//        }
        holder.binding.item=item
        holder.binding.imgItemClose.setOnClickListener{
            closeButtonOnClick(item)
        }
        Log.d(TAG, "onBindViewHolder: ${item.long}")

    }

}
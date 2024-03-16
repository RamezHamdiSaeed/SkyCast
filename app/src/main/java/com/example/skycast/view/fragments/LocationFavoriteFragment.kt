package com.example.skycast.view.fragments

import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skycast.databinding.FragmentLocationFavoriteBinding
import com.example.skycast.view.list.favorit.FavoritListAdapter
import com.example.skycast.model.WeatherInfo
import com.example.skycast.utility.Status
import com.example.skycast.view.activities.LocationSearchActivity
import com.example.skycast.viewModel.MyViewModel
import com.example.skycast.viewModel.MyViewModelSingleton
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationFavoriteFragment : Fragment() {

private val TAG="LocationFavoriteFragment"
    private lateinit var binding:FragmentLocationFavoriteBinding
    private lateinit var myViewModel: MyViewModel

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

        myViewModel=MyViewModelSingleton.sharedViewModel
        val connectivityManager: ConnectivityManager = context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
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
        }
        myViewModel.getLocationsDB()
        val favoritListAdapter= FavoritListAdapter(){
            MyViewModelSingleton.sharedViewModel.deleteLocation(it)
        }
        binding.favoriteList.adapter=favoritListAdapter

        binding.PbFavorit.visibility=View.GONE
        binding.favoriteList.visibility=View.VISIBLE

        binding.floatingActionButton.setOnClickListener{

            startActivity(Intent(requireActivity(),LocationSearchActivity::class.java))
        }

        handleCrudOperation(myViewModel.savedLocations, onSuccess = {info->
            val data: List<WeatherInfo> =info as List<WeatherInfo>
            Log.d(TAG, "onCreateView: data: $info")
//            binding.PbHourly.visibility=View.GONE
//            binding.hourlyList.visibility=View.VISIBLE
//            var hourlyList=dataManipulator.filterListForHourlyInfo(data.list)
//            hourlyListAdapter.submitList(hourlyList)
//
//            binding.PbDaily.visibility=View.GONE
//            binding.dailyList.visibility=View.VISIBLE
//            var dailyList=dataManipulator.filterListForDailyInfo(data.list)
//            dailyListAdapter.submitList(dailyList)
            Log.d(TAG, "onCreateView: data:${data}")
            favoritListAdapter.submitList(data)

        }, onFail = {
//            binding.PbHourly.visibility=View.VISIBLE
//            binding.hourlyList.visibility=View.GONE
//            if(!NoInternetDialogFragment.isTriggered){
//                NoInternetDialogFragment.show(requireActivity().supportFragmentManager, "NoInternetDialog")
//                NoInternetDialogFragment.isTriggered=!NoInternetDialogFragment.isTriggered
//            }
        }, onLoading = {
//            binding.PbHourly.visibility=View.VISIBLE
//            binding.hourlyList.visibility=View.GONE


        },"local data base data (locations)")

        return binding.root
    }
    fun handleCrudOperation(data: StateFlow<Status>, onSuccess:(data:Any)->Unit, onFail:()->Unit, onLoading:()->Unit, operationName:String="info"){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                data.collect { info ->
                    when (info) {
                        is Status.Loading -> {
                            onLoading()
                            Log.d(TAG, "onCreateView: $operationName: not retrieved yet")
                        }
                        is Status.Success -> {
                            onSuccess(info.data)
                            Log.d(TAG, "onCreateView: $operationName: ${info.data}")
                        }
                        else -> {
                            onFail()
                            Log.d(TAG, "onCreateView: $operationName: fail")
                        }
                    }
                }
            }
        }
    }

}
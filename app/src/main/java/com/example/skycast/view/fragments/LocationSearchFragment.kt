package com.example.skycast.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.skycast.databinding.FragmentLocationSearchBinding
import com.example.skycast.db.LocationsDB
import com.example.skycast.db.LocationsLocalDataSourceImp
import com.example.skycast.model.LocationInfo
import com.example.skycast.model.LocationWeatherRepositoryImp
import com.example.skycast.model.Weather
import com.example.skycast.model.WeatherInfo
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.utility.DataManipulator
import com.example.skycast.utility.NoInternetDialogFragment
import com.example.skycast.utility.Status
import com.example.skycast.view.activities.LocationInfoActivity
import com.example.skycast.viewModel.MyViewModel
import com.example.skycast.viewModel.MyViewModelFactory
import com.example.skycast.viewModel.MyViewModelSingleton
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LocationSearchFragment(val isForInsert:Boolean=false) : Fragment() {

    private val TAG="LocationSearchFragment"
    private lateinit var binding:FragmentLocationSearchBinding
    private lateinit var myViewModel: MyViewModel
    private  var currentLatitude: String="30.5853431"
    private  var currentLongitude: String="31.5035127"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        binding= FragmentLocationSearchBinding.inflate(layoutInflater, container, false)

        binding.mapWebView.webViewClient = WebViewClient()
        binding.mapWebView.settings.javaScriptEnabled = true
        binding.mapWebView.webChromeClient = WebChromeClient()
        binding.mapWebView.addJavascriptInterface(WebAppInterface(), "Android")
        myViewModel=MyViewModelSingleton.sharedViewModel

        val repository = LocationWeatherRepositoryImp(
            LocationsLocalDataSourceImp(LocationsDB.getInstance(requireActivity()).getProductsDao()),
            RemoteDataSourceImp()
        )
        val myLocationViewModel:MyViewModel= ViewModelProvider(this, MyViewModelFactory(repository)).get(MyViewModel::class.java)

        binding.btnConfirm.setOnClickListener {
//            binding.mapWebView.loadUrl("javascript:updateMarkerPosition($currentLatitude, $currentLongitude)")

    if(isForInsert){
    myLocationViewModel.getLocationInfoByCoordinatesAPI(currentLatitude,currentLongitude)
    handleCrudOperation(myLocationViewModel.locationInfoByCoordinates, onSuccess = {info->
        val data: LocationInfo =info as LocationInfo
        myViewModel.getCurrentWeatherConditionsAPI(currentLatitude,currentLongitude)
        handleCrudOperation(myViewModel.currentWeatherConditions, onSuccess = {info->
            val weatherConditions: Weather =info as Weather
            val dataManipulator= DataManipulator(requireActivity())
            myViewModel.insertLocation(WeatherInfo(data.address?.city?:data.address?.country!!,
                longitude = currentLongitude, lat = currentLatitude,
                temp=weatherConditions?.current?.temp.toString(),
                icon= dataManipulator.prepareImageUrl(weatherConditions?.current?.weather?.get(0)?.icon?:""),
                description = weatherConditions?.current?.weather?.get(0)?.description?:""))



        }, onFail = {
            myViewModel.insertLocation(WeatherInfo(data.address?.city?:data.address?.country!!, longitude = currentLongitude, lat = currentLatitude,"","",""))


        }, onLoading = {



        },"currentWeatherConditions")
        activity?.finish()
    }, onFail = {
        if(!NoInternetDialogFragment.isTriggered){
            NoInternetDialogFragment.show(requireActivity().supportFragmentManager, "NoInternetDialog")
            NoInternetDialogFragment.isTriggered=!NoInternetDialogFragment.isTriggered
        }

    }, onLoading = {

    },"locationInfoByCoordinates")

    }
    else
    {
        startActivity(Intent(requireActivity(), LocationInfoActivity::class.java).putExtra("latitude",currentLatitude).putExtra("longitude",currentLongitude))
    }
        }

        binding.mapWebView.loadUrl("file:///android_asset/map.html")
        return binding.root
    }
    inner class WebAppInterface {
        @JavascriptInterface
        fun onMarkerMoved(latitude: Double, longitude: Double) {
            currentLatitude=latitude.toString()
            currentLongitude=longitude.toString()
            Log.d(TAG, "onMarkerMoved: cLat: $currentLatitude, cLong: $currentLongitude")

        }
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
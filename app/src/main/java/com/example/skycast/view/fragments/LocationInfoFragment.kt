package com.example.skycast.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.skycast.R
import com.example.skycast.databinding.FragmentLocationInfoBinding
import com.example.skycast.model.LocationInfo
import com.example.skycast.model.LocationWeatherRepositoryImp
import com.example.skycast.model.Weather
import com.example.skycast.model.WeatherForecast
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.utility.DataManipulator
import com.example.skycast.utility.NoInternetDialogFragment
import com.example.skycast.utility.Status
import com.example.skycast.view.list.hourly.HourlyListAdapter
import com.example.skycast.view.list.model.WeatherBriefInfo
import com.example.skycast.viewModel.MyViewModel
import com.example.skycast.viewModel.MyViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class LocationInfoFragment : Fragment() {

    private val TAG = "LocationInfoFragment"
    private lateinit var dataManipulator: DataManipulator
    private lateinit var binding:FragmentLocationInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "onCreateView: ")

        binding=FragmentLocationInfoBinding.inflate(inflater,container,false)
        dataManipulator= DataManipulator(context = requireActivity())

//        hourlyList=view.findViewById(R.id.hourlyList)
        binding.hourlyList.layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        val adapter=HourlyListAdapter()
        adapter.submitList(listOf(WeatherBriefInfo("22","https://openweathermap.org/img/wn/10d@2x.png","06:00"),
            WeatherBriefInfo("33","https://openweathermap.org/img/wn/10d@2x.png","09:00"),
            WeatherBriefInfo("44","https://openweathermap.org/img/wn/10d@2x.png","12:00")
        ))
        binding.hourlyList.adapter=adapter



        val myViewModel: MyViewModel = ViewModelProvider(
            this,
            MyViewModelFactory(
                LocationWeatherRepositoryImp(
                    RemoteDataSourceImp()
                )
            )
        ).get(MyViewModel::class.java)
        myViewModel.getLocationInfoByCoordinatesAPI()
        myViewModel.getCurrentWeatherConditionsAPI()
        myViewModel.getCurrentWeatherForcastAPI()
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO){
//
//                Log.d(TAG, "onCreateView: fFFFFFFFFForcAAAAAAAAAAAAst ${fetchWeatherForecast("30.5853431","31.5035127","8e3540a48d29fcaa9704ffd3b94bad07")}")
//
//            }
//        }

        handleCrudOperation(myViewModel.currentWeatherConditions, onSuccess = {info->
            val data:Weather=info as Weather

            binding.PbCard.visibility=View.GONE
            binding.GCardInfo.visibility=View.VISIBLE

            binding.tvCurrentDate.text=dataManipulator.getDateYMD()
            binding.tvCurrentTime.text=dataManipulator.getDateYMD(DataManipulator.DateType.Time)
            binding.tvCurrentTemp.text=data?.current?.temp.toString()
            binding.tvCurrentWeatherDescription.text= data?.current?.weather?.get(0)?.description
            binding.tvChanceOfRainValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Else,data?.current?.clouds.toString())
            binding.tvHumidityValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Else,data?.current?.humidity.toString())
            binding.tvWindValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Wind,data?.current?.wind_speed.toString())
            binding.tvPressureValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Pressure,data?.current?.pressure.toString())
            dataManipulator.injectImage(data?.current?.weather?.get(0)?.icon!!,binding.imgCurrentWeatherIcon)
        }, onFail = {
            binding.PbCard.visibility=View.VISIBLE
            binding.GCardInfo.visibility=View.GONE

            if(!NoInternetDialogFragment.isTriggered){
                NoInternetDialogFragment.show(requireActivity().supportFragmentManager, "NoInternetDialog")
                NoInternetDialogFragment.isTriggered=!NoInternetDialogFragment.isTriggered
            }
        }, onLoading = {
            binding.PbCard.visibility=View.VISIBLE
            binding.GCardInfo.visibility=View.GONE

        },"currentWeatherConditions")

        handleCrudOperation(myViewModel.currentWeatherForecast, onSuccess = {info->
            val data:WeatherForecast=info as WeatherForecast

            binding.PbHourly.visibility=View.GONE
            binding.hourlyList.visibility=View.VISIBLE
//            binding.tvCurrentDate.text=dataManipulator.getDateYMD()
//            binding.tvCurrentTime.text=dataManipulator.getDateYMD(DataManipulator.DateType.Time)
//            binding.tvCurrentTemp.text=data?.current?.temp.toString()
//            binding.tvCurrentWeatherDescription.text= data?.current?.weather?.get(0)?.description
//            binding.tvChanceOfRainValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Else,data?.current?.clouds.toString())
//            binding.tvHumidityValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Else,data?.current?.humidity.toString())
//            binding.tvWindValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Wind,data?.current?.wind_speed.toString())
//            binding.tvPressureValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Pressure,data?.current?.pressure.toString())
//            dataManipulator.injectImage(data?.current?.weather?.get(0)?.icon!!,binding.imgCurrentWeatherIcon)
        }, onFail = {
            if(!NoInternetDialogFragment.isTriggered){
                NoInternetDialogFragment.show(requireActivity().supportFragmentManager, "NoInternetDialog")
                NoInternetDialogFragment.isTriggered=!NoInternetDialogFragment.isTriggered
            }
        }, onLoading = {
            binding.PbCard.visibility=View.VISIBLE
            binding.GCardInfo.visibility=View.GONE

        },"currentWeatherForecast")
        

        handleCrudOperation(myViewModel.locationInfoByCoordinates, onSuccess = {info->
                            val data:LocationInfo=info as LocationInfo
                            binding.tvCurrentCity.text=data.address?.city
        }, onFail = {
            if(!NoInternetDialogFragment.isTriggered){
                NoInternetDialogFragment.show(requireActivity().supportFragmentManager, "NoInternetDialog")
                NoInternetDialogFragment.isTriggered=!NoInternetDialogFragment.isTriggered
            }

        }, onLoading = {

        },"locationInfoByCoordinates")

        return binding.root
    }
    fun handleCrudOperation(data:StateFlow<Status>,onSuccess:(data:Any)->Unit,onFail:()->Unit,onLoading:()->Unit,operationName:String="info"){
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

//    suspend fun fetchWeatherForecast(lat: String, lon: String, apiKey: String): String {
//        val urlString = "https://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$lon&appid=$apiKey"
//
//        val url = URL(urlString)
//        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//        connection.requestMethod = "GET"
//
//        val responseCode = connection.responseCode
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            val reader = BufferedReader(InputStreamReader(connection.inputStream))
//            val response = StringBuilder()
//            var line: String?
//            while (reader.readLine().also { line = it } != null) {
//                response.append(line)
//            }
//            reader.close()
//            connection.disconnect()
//            return response.toString()
//        } else {
//            throw Exception("Failed to fetch weather forecast. Response code: $responseCode")
//        }
//    }

}

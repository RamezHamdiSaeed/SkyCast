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
import com.example.skycast.model.LocationInfo
import com.example.skycast.model.LocationWeatherRepositoryImp
import com.example.skycast.model.Weather
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.utility.DataManipulator
import com.example.skycast.utility.Status
import com.example.skycast.view.list.hourly.HourlyListAdapter
import com.example.skycast.view.list.model.WeatherBriefInfo
import com.example.skycast.viewModel.MyViewModel
import com.example.skycast.viewModel.MyViewModelFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationInfoFragment : Fragment() {

    private val TAG = "LocationInfoFragment"
    private lateinit var progressBar: ProgressBar
    private lateinit var cardGroup:Group

    private lateinit var tvCurrentCity:TextView
    private lateinit var tvCurrentDate:TextView
    private lateinit var tvCurrentTime:TextView
    private lateinit var tvCurrentTemp:TextView
    private lateinit var tvCurrentWeatherDescription:TextView
    private lateinit var tvChanceOfRainValue:TextView
    private lateinit var tvHumidityValue:TextView
    private lateinit var tvWindValue:TextView
    private lateinit var tvPressureValue:TextView
    private lateinit var imgCurrentWeatherIcon:ImageView
    private lateinit var dataManipulator: DataManipulator

    private lateinit var hourlyList:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ")
        val view: View = inflater.inflate(R.layout.fragment_location_info, container, false)
         progressBar=view.findViewById(R.id.PbCard)
        cardGroup=view.findViewById(R.id.GCardInfo)
        tvCurrentCity=view.findViewById(R.id.tvCurrentCity)
        tvCurrentDate=view.findViewById(R.id.tvCurrentDate)
        tvCurrentTime=view.findViewById(R.id.tvCurrentTime)
        tvCurrentTemp=view.findViewById(R.id.tvCurrentTemp)
        tvCurrentWeatherDescription=view.findViewById(R.id.tvCurrentWeatherDescription)
        tvChanceOfRainValue=view.findViewById(R.id.tvChanceOfRainValue)
        tvHumidityValue=view.findViewById(R.id.tvHumidityValue)
        tvWindValue=view.findViewById(R.id.tvWindValue)
        tvPressureValue=view.findViewById(R.id.tvPressureValue)
        imgCurrentWeatherIcon=view.findViewById(R.id.imgCurrentWeatherIcon)
        dataManipulator= DataManipulator(context = requireActivity())

        hourlyList=view.findViewById(R.id.hourlyList)
        hourlyList.layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        val adapter=HourlyListAdapter()
        adapter.submitList(listOf(WeatherBriefInfo("22","https://openweathermap.org/img/wn/10d@2x.png","06:00"),
            WeatherBriefInfo("33","https://openweathermap.org/img/wn/10d@2x.png","09:00"),
            WeatherBriefInfo("44","https://openweathermap.org/img/wn/10d@2x.png","12:00")
        ))
        hourlyList.adapter=adapter



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

        handleCrudOperation(myViewModel.currentWeatherConditions, onSuccess = {info->
            val data:Weather=info as Weather

            progressBar.visibility=View.GONE
            cardGroup.visibility=View.VISIBLE

            tvCurrentDate.text=dataManipulator.getDateYMD()
            tvCurrentTime.text=dataManipulator.getDateYMD(DataManipulator.DateType.Time)
            tvCurrentTemp.text=data?.current?.temp.toString()
            tvCurrentWeatherDescription.text= data?.current?.weather?.get(0)?.description
            tvChanceOfRainValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Else,data?.current?.clouds.toString())
            tvHumidityValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Else,data?.current?.humidity.toString())
            tvWindValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Wind,data?.current?.wind_speed.toString())
            tvPressureValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Pressure,data?.current?.pressure.toString())
            dataManipulator.injectImage(data?.current?.weather?.get(0)?.icon!!,imgCurrentWeatherIcon)
        }, onFail = {

        }, onLoading = {
            progressBar.visibility=View.VISIBLE
            cardGroup.visibility=View.GONE

        },"currentWeatherConditions")

        handleCrudOperation(myViewModel.locationInfoByCoordinates, onSuccess = {info->
                            val data:LocationInfo=info as LocationInfo
                            tvCurrentCity.text=data.address?.city
        }, onFail = {

        }, onLoading = {

        },"locationInfoByCoordinates")

        return view
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



}

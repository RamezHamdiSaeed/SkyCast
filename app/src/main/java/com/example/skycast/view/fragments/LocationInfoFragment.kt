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

import com.example.skycast.R
import com.example.skycast.model.LocationInfo
import com.example.skycast.model.LocationWeatherRepositoryImp
import com.example.skycast.model.Weather
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.utility.DataManipulator
import com.example.skycast.utility.Status
import com.example.skycast.viewModel.MyViewModel
import com.example.skycast.viewModel.MyViewModelFactory
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myViewModel.currentWeatherConditions.collect { weatherStatus ->
                    when (weatherStatus) {
                        is Status.Loading -> {
                            Log.d(TAG, "onCreateView: currentWeatherConditions: not retrieved yet")
                            progressBar.visibility=View.VISIBLE
                            cardGroup.visibility=View.GONE

                        }
                        is Status.Success -> {
                            println(weatherStatus.data)
                            progressBar.visibility=View.GONE
                            cardGroup.visibility=View.VISIBLE
                            val data:Weather=weatherStatus.data as Weather

                            tvCurrentDate.text=dataManipulator.getDateYMD()
                            tvCurrentTime.text=dataManipulator.getDateYMD(DataManipulator.DateType.Time)
                            tvCurrentTemp.text=data?.current?.temp.toString()
                            tvCurrentWeatherDescription.text= data?.current?.weather?.get(0)?.description
                            tvChanceOfRainValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Else,data?.current?.clouds.toString())
                            tvHumidityValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Else,data?.current?.humidity.toString())
                            tvWindValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Wind,data?.current?.wind_speed.toString())
                            tvPressureValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Pressure,data?.current?.pressure.toString())
                            dataManipulator.injectImage(data?.current?.weather?.get(0)?.icon!!,imgCurrentWeatherIcon)
                            Log.d(TAG, "onCreateView: currentWeatherConditions: ${weatherStatus.data}")
                        }
                        else -> {
                            Log.d(TAG, "onCreateView: currentWeatherConditions: fail")
                        }
                    }
                }


            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myViewModel.locationInfoByCoordinates.collect { locationStatus ->
                    when (locationStatus) {
                        is Status.Loading -> {
                            Log.d(TAG, "onCreateView: locationInfoByCoordinates: not retrieved yet")
                        }
                        is Status.Success -> {
                            println(locationStatus.data)
                            val data:LocationInfo=locationStatus.data as LocationInfo
                            tvCurrentCity.text=data.address?.city
                            Log.d(TAG, "onCreateView: locationInfoByCoordinates: ${locationStatus.data}")
                        }
                        else -> {
                            Log.d(TAG, "onCreateView: locationInfoByCoordinates: fail")
                        }
                    }
                }
            }
        }

        return view
    }


}

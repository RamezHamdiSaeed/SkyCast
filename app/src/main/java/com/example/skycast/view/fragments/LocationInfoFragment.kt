package com.example.skycast.view.fragments

import android.annotation.SuppressLint

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skycast.databinding.FragmentLocationInfoBinding
import com.example.skycast.db.LocationsDB
import com.example.skycast.db.LocationsDao
import com.example.skycast.db.LocationsLocalDataSource
import com.example.skycast.db.LocationsLocalDataSourceImp
import com.example.skycast.model.LocationInfo
import com.example.skycast.model.LocationWeatherRepositoryImp
import com.example.skycast.model.Weather
import com.example.skycast.model.WeatherForecast
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.utility.DataManipulator
import com.example.skycast.utility.NoInternetDialogFragment
import com.example.skycast.utility.Status
import com.example.skycast.view.list.daily.DailyListAdapter
import com.example.skycast.view.list.hourly.HourlyListAdapter
import com.example.skycast.viewModel.MyViewModel
import com.example.skycast.viewModel.MyViewModelFactory
import com.example.skycast.viewModel.MyViewModelSingleton
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


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
        
        binding=FragmentLocationInfoBinding.inflate(inflater,container,false)
        dataManipulator= DataManipulator(context = requireActivity())

        binding.hourlyList.layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        binding.dailyList.layoutManager=LinearLayoutManager(requireActivity())

        val hourlyListAdapter=HourlyListAdapter()
        binding.hourlyList.adapter=hourlyListAdapter

        val dailyListAdapter=DailyListAdapter()
        binding.dailyList.adapter=dailyListAdapter
        val repository = LocationWeatherRepositoryImp(
            LocationsLocalDataSourceImp(LocationsDB.getInstance(requireActivity()).getProductsDao()),
            RemoteDataSourceImp()
        )
        val myViewModel: MyViewModel= ViewModelProvider(this, MyViewModelFactory(repository)).get(MyViewModel::class.java)


        myViewModel.getLocationInfoByCoordinatesAPI()
        myViewModel.getCurrentWeatherConditionsAPI()
        myViewModel.getCurrentWeatherForcastAPI()

        handleCrudOperation(myViewModel.currentWeatherConditions, onSuccess = {info->
            val data:Weather=info as Weather

            binding.PbCard.visibility=View.GONE
            binding.GCardInfo.visibility=View.VISIBLE

            binding.tvCurrentDate.text=dataManipulator.getDateYMD()
            binding.tvCurrentTime.text=dataManipulator.getDateYMD(DataManipulator.DateType.Time)
            binding.tvCurrentTemp.text= dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Temp,data?.current?.temp.toString())
            binding.tvCurrentWeatherDescription.text= data?.current?.weather?.get(0)?.description
            binding.tvChanceOfRainValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Else,data?.current?.clouds.toString())
            binding.tvHumidityValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Else,data?.current?.humidity.toString())
            binding.tvWindValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Wind,data?.current?.wind_speed.toString())
            binding.tvPressureValue.text=dataManipulator.getValueWithMeasureUnit(DataManipulator.DataType.Pressure,data?.current?.pressure.toString())
            dataManipulator.injectImage(data?.current?.weather?.get(0)?.icon!!,binding.imgCurrentWeatherIcon)
        }, onFail = {
            Log.d(TAG, "onCreateView: ${it}")
            binding.PbCard.visibility=View.VISIBLE
            binding.GCardInfo.visibility=View.GONE
            if(!NoInternetDialogFragment.isTriggered){
                NoInternetDialogFragment.show(requireActivity().supportFragmentManager, "NoInternetDialog")
                NoInternetDialogFragment.isTriggered=!NoInternetDialogFragment.isTriggered
            }
        }, onLoading = {
            binding.PbCard.visibility=View.VISIBLE
            binding.GCardInfo.visibility=View.GONE
            Log.d(TAG, "onCreateView: loading")


        },"currentWeatherConditions")

        handleCrudOperation(myViewModel.currentWeatherForecast, onSuccess = {info->
            val data:WeatherForecast=info as WeatherForecast

            binding.PbHourly.visibility=View.GONE
            binding.hourlyList.visibility=View.VISIBLE
            var hourlyList=dataManipulator.filterListForHourlyInfo(data.list)
            hourlyListAdapter.submitList(hourlyList)

            binding.PbDaily.visibility=View.GONE
            binding.dailyList.visibility=View.VISIBLE
            var dailyList=dataManipulator.filterListForDailyInfo(data.list)
            dailyListAdapter.submitList(dailyList)

        }, onFail = {
            binding.PbHourly.visibility=View.VISIBLE
            binding.hourlyList.visibility=View.GONE
            if(!NoInternetDialogFragment.isTriggered){
                NoInternetDialogFragment.show(requireActivity().supportFragmentManager, "NoInternetDialog")
                NoInternetDialogFragment.isTriggered=!NoInternetDialogFragment.isTriggered
            }
        }, onLoading = {
            binding.PbHourly.visibility=View.VISIBLE
            binding.hourlyList.visibility=View.GONE


        },"currentWeatherForecast")
        

        handleCrudOperation(myViewModel.locationInfoByCoordinates, onSuccess = {info->
                            val data:LocationInfo=info as LocationInfo
                            binding.tvCurrentCity.text=data.address?.city
        }, onFail = {
            if(!NoInternetDialogFragment.isTriggered){
                NoInternetDialogFragment.show(requireActivity().supportFragmentManager, "NoInternetDialog")
                NoInternetDialogFragment.isTriggered=!NoInternetDialogFragment.isTriggered
            }
            Log.d(TAG, "onCreateView: fail when getting location")

        }, onLoading = {

        },"locationInfoByCoordinates")

        return binding.root
    }
    fun handleCrudOperation(data:StateFlow<Status>,onSuccess:(data:Any)->Unit,onFail:(msg:String)->Unit,onLoading:()->Unit,operationName:String="info"){
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
                        is Status.Fail -> {
                            onFail(info.msg.toString())
                            Log.d(TAG, "onCreateView: $operationName: fail")

                        }
                    }
                }
            }
        }
    }


}

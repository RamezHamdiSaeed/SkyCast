package com.example.skycast.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.skycast.R
import com.example.skycast.databinding.ActivityLocationSearchBinding
import com.example.skycast.db.LocationsDB
import com.example.skycast.db.LocationsLocalDataSourceImp
import com.example.skycast.model.LocationWeatherRepositoryImp
import com.example.skycast.model.WeatherInfo
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.view.fragments.LocationFavoriteFragment
import com.example.skycast.view.fragments.LocationSearchFragment
import com.example.skycast.viewModel.MyViewModel
import com.example.skycast.viewModel.MyViewModelFactory
import com.example.skycast.viewModel.MyViewModelSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationSearchActivity : AppCompatActivity() {
    private val TAG:String="LocationSearchActivity"
    private lateinit var locationSearchFragment: LocationSearchFragment
    private lateinit var fragmentMngr:FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    lateinit var binding:ActivityLocationSearchBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLocationSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationSearchFragment=LocationSearchFragment()
        fragmentMngr=supportFragmentManager
        fragmentTransaction=fragmentMngr.beginTransaction()
        fragmentTransaction.replace(binding.searchFragmentContainerView.id,locationSearchFragment,"Dynamic Injection")
        fragmentTransaction.commit()


    }

}
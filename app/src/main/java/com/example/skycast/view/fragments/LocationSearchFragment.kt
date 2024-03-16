package com.example.skycast.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.skycast.databinding.FragmentLocationSearchBinding


class LocationSearchFragment : Fragment() {

    private val TAG="LocationSearchFragment"
    private lateinit var binding:FragmentLocationSearchBinding

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

        binding.btnConfirm.setOnClickListener {
            val location = binding.etLocationSearch.text.toString()

            binding.mapWebView.loadUrl("javascript:updateMarkerPosition(30.5853431, 31.5035127)")
        }

        binding.mapWebView.loadUrl("file:///android_asset/map.html")
        return binding.root
    }
    inner class WebAppInterface {
        @JavascriptInterface
        fun onMarkerMoved(latitude: Double, longitude: Double) {
            Log.d(TAG, "onMarkerMoved: long: $longitude, lat: $latitude")
        }
    }

}
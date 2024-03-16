package com.example.skycast.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")

data class WeatherInfo(@PrimaryKey  var city:String, var longitude:String, var lat:String, var temp:String, var icon:String, var description:String){
    constructor() : this("", "", "", "", "", "")
}
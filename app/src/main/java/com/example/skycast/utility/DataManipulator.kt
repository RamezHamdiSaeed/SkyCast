package com.example.skycast.utility

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DataManipulator (val context: Context){
 private val MY_PREFERENCE="myPref"
    private var pref:SharedPreferences=context.getSharedPreferences(MY_PREFERENCE,Context.MODE_PRIVATE)
    val editor = pref.edit()

    public fun injectImage(icon:String,imageView: ImageView){
        Glide.with(context).load("https://openweathermap.org/img/wn/$icon@2x.png").into(imageView)
    }


    private fun getCurrentDateInFormat(format:String):String{
        val dateFormat=SimpleDateFormat(format, Locale.ENGLISH)
        val currentDate= Date(System.currentTimeMillis())
        return dateFormat.format(currentDate)
    }

    fun getDateYMD(dateType: DateType=DateType.Date):String{
        when(dateType){
            DateType.Date->{
                return getCurrentDateInFormat("yyyy-MM-dd")
            }
            else->{
                return getCurrentDateInFormat("HH-mm")
            }
        }
    }

    fun getValueWithMeasureUnit(dataType: DataType,plainValue:String):String{
        when (dataType){
            DataType.Temp ->{
               return plainValue+pref.getString(DataType.Temp.toString(),"°C")

            }
            DataType.Wind->{
                return plainValue+" "+pref.getString(DataType.Wind.toString(),"meter/sec")

            }
            DataType.Pressure->{
                return plainValue+" "+"hPa"

            }
            else->{
                return plainValue+" "+"%"

            }
        }

    }
    enum class DataType{
        Temp,
        Wind,
        Pressure,
        Else
    }
    enum class DateType{
        Date,
        Time

    }
}
package com.example.skycast.utility

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.skycast.model.ListItem
import com.example.skycast.view.list.model.WeatherBriefInfo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class DataManipulator (val context: Context){
 private val MY_PREFERENCE="myPref"
    private var pref:SharedPreferences=context.getSharedPreferences(MY_PREFERENCE,Context.MODE_PRIVATE)
    val editor = pref.edit()
    public fun filterListForHourlyInfo(listFromResponse: List<ListItem?>?):List<WeatherBriefInfo>{
         var hourlyList:MutableList<WeatherBriefInfo> = mutableListOf()
         val date=listFromResponse?.get(0)?.dt_txt?.split(" ")?.get(0)
        listFromResponse?.forEach{
            if(it?.dt_txt?.split(" ")?.get(0)==date){
                hourlyList.add(
                    WeatherBriefInfo(getValueWithMeasureUnit(DataType.Temp,it?.main?.temp.toString()) ,
                   prepareImageUrl(it?.weather?.get(0)?.icon.toString()) ,
                    it?.dt_txt?.split(" ")?.get(1)?.split(":")?.get(0)+":00"!!)
                )
            }
            else return hourlyList

        }
        return hourlyList
    }
    public fun filterListForDailyInfo(listFromResponse: List<ListItem?>?):List<WeatherBriefInfo>{
        var hourlyList:MutableList<WeatherBriefInfo> = mutableListOf()
        var date=listFromResponse?.get(0)?.dt_txt?.split(" ")?.get(0)
        listFromResponse?.forEach{
            var thisDate:String=it?.dt_txt?.split(" ")?.get(0)!!
            if(thisDate!=date){
                date=thisDate
                hourlyList.add(
                    WeatherBriefInfo(getValueWithMeasureUnit(DataType.Temp,it?.main?.temp.toString()) ,
                    prepareImageUrl(it?.weather?.get(0)?.icon.toString()),
                    getDayOfWeekAbbreviated(it?.dt_txt?.split(" ")?.get(0)!!))
                )
            }


        }
        return hourlyList
    }
    fun getDayOfWeekAbbreviated(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dayOfWeekAbbreviated = SimpleDateFormat("EEE", Locale.getDefault())
        return dayOfWeekAbbreviated.format(date)
    }
    public fun injectImage(icon:String,imageView: ImageView){
        Glide.with(context).load(prepareImageUrl(icon)).into(imageView)
    }
    public fun prepareImageUrl(url:String):String{
        return "https://openweathermap.org/img/wn/$url@2x.png"
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
    public fun changeMeasureUnit(dataType: DataType,value:String){
        editor.putString(dataType.toString(),value)
        editor.apply()
    }

    fun getValueWithMeasureUnit(dataType: DataType,plainValue:String):String{
        when (dataType){
            DataType.Temp ->{
               val measureUnit:String= pref.getString(DataType.Temp.toString(),"°C")?:"°C"
                return when(measureUnit){
                    "°C"->plainValue+pref.getString(DataType.Temp.toString(),"°C")
                    "K"->(plainValue.toDouble()+273.15).roundToInt().toString()+pref.getString(DataType.Temp.toString(),"K")
                    else->((plainValue.toDouble()*9/5)+32).roundToInt().toString()+pref.getString(DataType.Temp.toString(),"F")
                }


            }
            DataType.Wind->{
                val measureUnit:String= pref.getString(DataType.Wind.toString(),"meter/sec")?:"meter/sec"

                return when(measureUnit){
                    "meter/sec"->plainValue+pref.getString(DataType.Wind.toString(),"meter/sec")
                    else->(plainValue.toDouble()*2.23694).roundToInt().toString()+pref.getString(DataType.Wind.toString(),"miles/hour")
                }

            }
            DataType.Pressure->{
                return plainValue+" "+"hPa"

            }
            else->{
                return "% $plainValue"

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
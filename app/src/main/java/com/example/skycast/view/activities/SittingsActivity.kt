package com.example.skycast.view.activities

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.skycast.R
import com.example.skycast.databinding.ActivitySittingsBinding
import com.example.skycast.utility.DataManipulator
import java.util.Locale

class SittingsActivity : AppCompatActivity() {
    lateinit var binding:ActivitySittingsBinding
    lateinit var dataManipulator: DataManipulator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySittingsBinding.inflate(layoutInflater)
        dataManipulator= DataManipulator(this)

        val temperatureUnits = resources.getStringArray(R.array.temperatureUnits)
        val windUnits = resources.getStringArray(R.array.windUnits)
        val languages = resources.getStringArray(R.array.languages)
        val locationOptions = resources.getStringArray(R.array.locationOptions)



        binding.spTemp.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,temperatureUnits)
        binding.spWindSpeed.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,windUnits)
        binding.spLanguage.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,languages)
        binding.spLocation.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,locationOptions)




        binding.spTemp.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(temperatureUnits[position]){
                    "Celsius"-> dataManipulator.changeMeasureUnit(DataManipulator.DataType.Temp,"°C")
                     "Fahrenheit"->dataManipulator.changeMeasureUnit(DataManipulator.DataType.Temp,"F")
                     else->dataManipulator.changeMeasureUnit(DataManipulator.DataType.Temp,"K")

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.spWindSpeed.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(windUnits[position]){
                    "meter/sec"-> dataManipulator.changeMeasureUnit(DataManipulator.DataType.Wind,"meter/sec")
                    "miles/hour"->dataManipulator.changeMeasureUnit(DataManipulator.DataType.Wind,"miles/hour")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.spLanguage.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(languages[position]){
                    languages[1]-> setLocale("en")
                    languages[2]->setLocale("ar")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.spLocation.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        setContentView(binding.root)
    }
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
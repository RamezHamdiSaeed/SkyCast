package com.example.skycast.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.skycast.R
import com.example.skycast.databinding.ActivitySittingsBinding

class SittingsActivity : AppCompatActivity() {
    lateinit var binding:ActivitySittingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySittingsBinding.inflate(layoutInflater)

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
                Toast.makeText(this@SittingsActivity, "selected text: ${temperatureUnits[position]}", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@SittingsActivity, "selected text: ${windUnits[position]}", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@SittingsActivity, "selected text: ${languages[position]}", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@SittingsActivity, "selected text: ${locationOptions[position]}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }



        setContentView(binding.root)
    }
}
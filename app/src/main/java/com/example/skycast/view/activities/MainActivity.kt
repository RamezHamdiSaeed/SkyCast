package com.example.skycast.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.skycast.R
import com.example.skycast.databinding.ActivityMainBinding
import com.example.skycast.db.LocationsDB
import com.example.skycast.db.LocationsLocalDataSourceImp
import com.example.skycast.model.LocationWeatherRepositoryImp
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.viewModel.MyViewModel
import com.example.skycast.viewModel.MyViewModelFactory
import com.example.skycast.viewModel.MyViewModelSingleton



class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var actionBarDrawerToggle:ActionBarDrawerToggle
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = LocationWeatherRepositoryImp(
            LocationsLocalDataSourceImp(LocationsDB.getInstance(this).getProductsDao()),
            RemoteDataSourceImp()
        )
        MyViewModelSingleton.sharedViewModel= ViewModelProvider(this, MyViewModelFactory(repository)).get(MyViewModel::class.java)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController= Navigation.findNavController(this,R.id.fragmentContainerView)

        NavigationUI.setupWithNavController(binding.bottomNavigationBar,navController)


        actionBarDrawerToggle= ActionBarDrawerToggle(this,binding.myDrawer,R.string.open,R.string.close)

        binding.myDrawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navigationView.setNavigationItemSelectedListener{
            item ->
            when (item.itemId){
            R.id.settingsDrawer->{
                startActivity(Intent(this,SittingsActivity::class.java))
            }
            R.id.alertsDrawer->{
                startActivity(Intent(this,AlertsActivity::class.java))

            }
        }
            binding.myDrawer.closeDrawer(GravityCompat.START)
            true
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
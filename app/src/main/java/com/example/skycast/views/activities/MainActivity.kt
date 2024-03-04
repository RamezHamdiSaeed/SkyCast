package com.example.skycast.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.skycast.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navController: NavController
    //drawer to be opened and closed
    lateinit var drawerLayout: DrawerLayout;
    lateinit var actionBarDrawerToggle:ActionBarDrawerToggle
    lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView=findViewById(R.id.bottom_navigation_bar)
        navController= Navigation.findNavController(this,R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(bottomNavigationView,navController)
        //drawer
        drawerLayout=findViewById(R.id.myDrawer)
        actionBarDrawerToggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView=findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener{
            item ->
            when (item.itemId){
            R.id.settingsDrawer->{
                startActivity(Intent(this,SittingsActivity::class.java))
            }
            R.id.alertsDrawer->{
                startActivity(Intent(this,AlertsActivity::class.java))

            }
        }
            drawerLayout.closeDrawer(GravityCompat.START)
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
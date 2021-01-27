package com.edu.hoang

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.edu.hoang.databinding.ActivityMainBinding
import com.edu.hoang.ui.dashboard.DashboardFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var navController: NavController

    companion object {
        const val REQUEST_REMINDER = 10
        const val PERSONAL_ID = "com.edu.hoang.PERSONAL_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setSupportActionBar(mainBinding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = mainBinding.drawerLayout
        val navView: NavigationView = mainBinding.navView
        navController = findNavController(R.id.nav_host_fragment)
        val dashboardArgs = Bundle().apply {
            val id = intent.extras?.getLong(PERSONAL_ID) ?: 0
            putLong(DashboardFragment.PERSONAL_ID_KEY, id)
        }
        navController.setGraph(R.navigation.mobile_navigation, dashboardArgs)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dashboard,
                R.id.nav_prescription,
                R.id.nav_doctor,
                R.id.nav_test,
                R.id.nav_settings,
                R.id.nav_map
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mainBinding.appBarMain.fab.setOnClickListener {
            navController.navigate(R.id.nav_test)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}


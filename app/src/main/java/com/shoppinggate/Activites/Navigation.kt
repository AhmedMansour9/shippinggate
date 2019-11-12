package com.shoppinggate.Activites

import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.shoppinggate.Model.Profile_Response
import com.shoppinggate.R
import com.shoppinggate.ViewModel.Profile_ViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.nav_header_navigation.*

class Navigation : AppCompatActivity() {
    lateinit var UserToken: String
    private lateinit var DataSaver: SharedPreferences

    private lateinit var appBarConfiguration: AppBarConfiguration
    companion object {
        var toolbar: Toolbar?=null
        var drawerLayout: DrawerLayout?=null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        UserToken = DataSaver.getString("token", null)!!

        Get_Profle()
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_AllCategories, R.id.nav_mywishlist,
                R.id.nav_myorders, R.id.nav_language, R.id.nav_sharewithfriends,R.id.nav_rateus
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun Get_Profle() {
        var Prof_ViewModel: Profile_ViewModel =
            ViewModelProviders.of(this)[Profile_ViewModel::class.java]

        Prof_ViewModel.getData(
            UserToken,

            applicationContext
        ).observe(this,
            Observer<Profile_Response> { loginmodel ->

                if (loginmodel != null) {
                    Title.setText(loginmodel.data.get(0).name)
                    Email.setText(loginmodel.data.get(0).email)
                } else {

                }
            }
        )
    }

}

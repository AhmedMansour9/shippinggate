package com.shoppinggate.Activites.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.shoppinggate.Activites.Navigation.Companion.drawerLayout
import com.shoppinggate.Fragments.AllpopularProducts
import com.shoppinggate.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
     var root:View?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
         root = inflater.inflate(R.layout.fragment_home, container, false)
        init()
        openAllPopular()


        return root
    }

    private fun openAllPopular() {
        root!!.T_AllPopular.setOnClickListener(){

            var cart=AllpopularProducts()
            val bundle = Bundle()
            cart.arguments=bundle
            fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home, cart)
                ?.addToBackStack(null)?.commit()

        }
    }

    fun init() {
        val toggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            root!!.toolbarHome,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.apply {
            syncState()
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.icon_navigation)
            setToolbarNavigationClickListener { drawerLayout!!.openDrawer(GravityCompat.START) }
        }
        drawerLayout?.addDrawerListener(toggle)

    }
}
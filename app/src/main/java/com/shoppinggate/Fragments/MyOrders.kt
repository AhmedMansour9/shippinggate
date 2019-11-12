package com.shoppinggate.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.shoppinggate.Activites.Navigation

import com.shoppinggate.R
import kotlinx.android.synthetic.main.fragment_allpopular_products.view.*
import kotlinx.android.synthetic.main.fragment_my_orders.view.*

/**
 * A simple [Fragment] subclass.
 */
class MyOrders : Fragment() {
     var root:View?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_my_orders, container, false)
        init()

        return root
    }


    fun init() {
        val toggle = ActionBarDrawerToggle(
            activity,
            Navigation.drawerLayout,
            root!!.toolbarMyorders,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.apply {
            syncState()
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.icon_navigation)
            setToolbarNavigationClickListener { Navigation.drawerLayout!!.openDrawer(GravityCompat.START) }
        }
        Navigation.drawerLayout?.addDrawerListener(toggle)

    }

}

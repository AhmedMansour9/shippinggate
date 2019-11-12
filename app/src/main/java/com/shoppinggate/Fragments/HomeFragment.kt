package com.shoppinggate.Fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.shoppinggate.Activites.Navigation.Companion.drawerLayout
import com.shoppinggate.Model.SliderHome_Model
import com.shoppinggate.R
import com.shoppinggate.ViewModel.SliderHome_ViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.shoppinggate.Activites.ui.home.HomeViewModel
import com.shoppinggate.Adapter.*
import com.shoppinggate.Model.AllProducts_Response
import com.shoppinggate.Model.Categories_Response
import com.shoppinggate.View.ProductById_View
import com.shoppinggate.ViewModel.Categories_ViewModel
import com.shoppinggate.ViewModel.getAllProducts_ViewModel


class HomeFragment : Fragment() , SwipeRefreshLayout.OnRefreshListener, ProductById_View {

    var swipeTimer: Timer?=null
    val handler = Handler()
    val Update = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0
        }
        viewPager!!.setCurrentItem(currentPage++, true)
    }
    private var currentPage = 0
    private var NUM_PAGES = 0

    private lateinit var homeViewModel: HomeViewModel
     var root: View?=null
    lateinit var DeviceLang:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         root = inflater.inflate(R.layout.fragment_home, container, false)

        init()
        Language()
        openAllPopular()
        SwipRefresh()
        openAllOffer()


        return root
    }

    private fun openAllOffer() {
        root!!.T_AllOffers.setOnClickListener(){
            var cart= AllProducts_Fragment()
            val bundle = Bundle()
            bundle.putString("type","offer")
            cart.arguments=bundle
            fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home, cart)
                ?.addToBackStack(null)?.commit()
        }
    }

    fun SwipRefresh(){
        root!!.SwipHome.setOnRefreshListener(this)
        root!!.SwipHome.isRefreshing=true
        root!!.SwipHome.isEnabled = true
        root!!.SwipHome.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        root!!.SwipHome.post(Runnable {
            getSlider()
            getOffersProducts()
            getAllCategories()
//            getNumberOfCart()
            getpopularProducts()

        })
    }

    private fun openAllPopular() {
        root!!.T_AllPopular.setOnClickListener(){
            var cart= AllProducts_Fragment()
            val bundle = Bundle()
            bundle.putString("type","popular")
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


    fun Language() {
        DeviceLang = Locale.getDefault().language
    }

    fun getSlider(){
        var SliderHome:SliderHome_ViewModel= ViewModelProviders.of(this)[SliderHome_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            SliderHome.getData(DeviceLang, it)?.observe(this, Observer<SliderHome_Model> { loginmodel ->
                root!!.SwipHome.isRefreshing=false
                if(loginmodel!=null) {
                    viewPager!!.adapter = this.context?.let { it1 ->
                        Slider_Adapter(
                            it1,
                            loginmodel.data as ArrayList<SliderHome_Model.Slider_Home>
                        )
                    }
                    root!!.view_pager_circle_indicator.setViewPager(viewPager)

                    NUM_PAGES = loginmodel.data!!.size
                    swipeTimer = Timer()
                    swipeTimer!!.schedule(object : TimerTask() {
                        override fun run() {
                            handler.post(Update)
                        }
                    }, 3000, 3000)

                    viewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                        override fun onPageSelected(position: Int) {
                            currentPage = position
                        }
                        override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {
                        }
                        override fun onPageScrollStateChanged(pos: Int) {

                        }
                    })
                }
            })
        }
    }

    fun getAllCategories(){
        var categories: Categories_ViewModel = ViewModelProviders.of(this)[Categories_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            categories.getCategories(DeviceLang, it).observe(this, Observer<Categories_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val listAdapter =
                        Categories_Adapter(context!!.applicationContext, loginmodel.data)
                    listAdapter.onClick(this)
                    recycler_Categroies.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    recycler_Categroies.setAdapter(listAdapter)
                }

            })
        }
    }

    fun getOffersProducts(){
        var allproducts: getAllProducts_ViewModel = ViewModelProviders.of(this)[getAllProducts_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            allproducts.getLatest(DeviceLang, it)?.observe(this, Observer<AllProducts_Response> { loginmodel ->
                if(loginmodel!=null) {
                    var listAdapter =
                        OffersProducts_Adapter(context!!.applicationContext, loginmodel.data)
//                    listAdapter.onClick(this)
                    recycler_BestOffer.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    recycler_BestOffer.setAdapter(listAdapter)

                }
            })
        }
    }

    fun getpopularProducts(){
        var allproducts: getAllProducts_ViewModel = ViewModelProviders.of(this)[getAllProducts_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            allproducts.getpopular(DeviceLang, it)?.observe(this, Observer<AllProducts_Response> { loginmodel ->
                if(loginmodel!=null) {
                    var listAdapter =
                        Popular_Adapter(context!!.applicationContext, loginmodel.data)
//                    listAdapter.onClick(this)
                    recycler_PopularProducts.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    recycler_PopularProducts.setAdapter(listAdapter)

                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(Update)
        if(swipeTimer!=null) {
            swipeTimer!!.cancel()
        }    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(Update)
        if(swipeTimer!=null) {
            swipeTimer!!.cancel()
        }
    }

    override fun onDetach() {
        super.onDetach()
        handler.removeCallbacks(Update)
        if(swipeTimer!=null) {
            swipeTimer!!.cancel()
        }
    }

    override fun Id(categories: Categories_Response.CategoriesDetails) {
        var cart= AllProducts_Fragment()
        val bundle = Bundle()
        bundle.putString("type","cat")
        bundle.putParcelable("categories",categories)
        cart.arguments=bundle
        fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home, cart)
            ?.addToBackStack(null)?.commit()
    }

    override fun onRefresh() {
        root!!.SwipHome.isRefreshing=true
        getSlider()
        getOffersProducts()
        getAllCategories()
    }

}
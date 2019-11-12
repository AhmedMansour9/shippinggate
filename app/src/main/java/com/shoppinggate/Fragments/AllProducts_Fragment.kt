package com.shoppinggate.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.shoppinggate.Activites.Navigation

import com.shoppinggate.R
import kotlinx.android.synthetic.main.fragment_allpopular_products.view.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.shoppinggate.Adapter.AllProducts_Adapter
import com.shoppinggate.Adapter.Sorts_Adapter
import com.shoppinggate.Model.AllProducts_Response
import com.shoppinggate.Model.Categories_Response
import com.shoppinggate.View.ProductDetails
import com.shoppinggate.ViewModel.getAllProducts_ViewModel
import kotlinx.android.synthetic.main.fragment_allpopular_products.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class AllProducts_Fragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, ProductDetails {



    var root:View?=null
    lateinit var listProducts:List<AllProducts_Response.AllProducts_Model>
    lateinit var DeviceLang:String
    var bundle=Bundle()
    lateinit var categories: Categories_Response.CategoriesDetails

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>
    var  sheetdirec:RelativeLayout?=null
    lateinit var T_Cancel:TextView
     var recycler_Sort:RecyclerView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_allpopular_products, container, false)
         init()
        Language()
        ButtonSheet()
        openSort()
        getData()
        ArraySorts()
        cancelSort()
        openfilter()
        return root
    }
    fun getData(){
        var bundlle:Bundle=this.arguments!!
      var Type:String=bundlle.getString("type")!!
        if(Type.equals("offer")){
            root!!.T_Title.text=context!!.applicationContext.resources.getString(R.string.bestoffer)

        }else if(Type.equals("popular")){
            root!!.T_Title.text=context!!.applicationContext.resources.getString(R.string.popularproducts)
            getpopularProducts()

        }else if(Type.equals("cat")){

         getFilterProducts()


        }
    }

    fun getFilterProducts(){

        bundle = this!!.arguments!!
        categories = bundle.getParcelable("categories")!!
        root!!.T_Title.text=categories.title
        var allproducts: getAllProducts_ViewModel = ViewModelProviders.of(this)[getAllProducts_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            allproducts.getProductsId(categories.id.toString(),DeviceLang, it)?.observe(this, Observer<AllProducts_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val listAdapter =
                        AllProducts_Adapter(context!!.applicationContext, loginmodel.data)
                    listAdapter.onClick(this)
                    recycler_Products.setLayoutManager(
                        GridLayoutManager(
                            context!!.applicationContext
                            , 2
                        )
                    )
                    recycler_Products.setAdapter(listAdapter)
                }

            })
        }
    }

    fun getpopularProducts(){
        root!!.SwipProducts.isRefreshing=true

        var allproducts: getAllProducts_ViewModel = ViewModelProviders.of(this)[getAllProducts_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            allproducts.getpopular(DeviceLang, it)?.observe(this, Observer<AllProducts_Response> { loginmodel ->
                root!!.SwipProducts.isRefreshing=false

                if(loginmodel!=null) {
                    listProducts=loginmodel.data
                    var listAdapter =
                        AllProducts_Adapter(context!!.applicationContext, listProducts)
                    listAdapter.onClick(this)
                    recycler_Products.setLayoutManager(
                        GridLayoutManager(
                            context!!.applicationContext
                            , 2
                        )
                    )
                    recycler_Products.setAdapter(listAdapter)

                }
            })
        }
    }

    fun openfilter(){
        root!!.Btn_Filter.setOnClickListener(){

            var cart=Filtertion_Fragment()
            val bundle = Bundle()
            cart.arguments=bundle
            fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home, cart)
                ?.addToBackStack(null)?.commit()

        }


    }

    fun init() {
        T_Cancel=root!!.findViewById(R.id.T_Cancel)
        recycler_Sort=root!!.findViewById(R.id.recycler_Sorts);
        root!!.SwipProducts.setOnRefreshListener(this)

        val toggle = ActionBarDrawerToggle(
            activity,
            Navigation.drawerLayout,
            root!!.toolbarpopularproducts,
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

    fun openSort(){
        root!!.icon_sort.setOnClickListener(){
//            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
            }
        }
    }

    fun ButtonSheet() {
        sheetdirec=root!!.findViewById(R.id.sheetdirec)
         bottomSheetBehavior = BottomSheetBehavior.from(sheetdirec)
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

    }

    fun ArraySorts(){
        val myArray:List<String> = root!!.resources.getStringArray(R.array.sorts).toList()
        val listAdapter =
            Sorts_Adapter(context!!.applicationContext, myArray)
        recycler_Sort!!.layoutManager = LinearLayoutManager(
            this.context!!.applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        recycler_Sort!!.setAdapter(listAdapter)

    }

    fun cancelSort(){
        T_Cancel.setOnClickListener(){

            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)


        }
    }

    fun Language() {
        DeviceLang = Locale.getDefault().language
    }

    override fun onRefresh() {
        getData()
    }

    override fun details(list: AllProducts_Response.AllProducts_Model) {

        var products=Details_Product()
        val bundle = Bundle()
        bundle.putParcelable("ProductItem", list)
        products.arguments=bundle
        fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home, products)
            ?.addToBackStack(null)?.commit()
    }
}

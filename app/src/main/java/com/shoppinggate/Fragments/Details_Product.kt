package com.shoppinggate.Fragments


import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.shoppinggate.Adapter.SliderProducts_Adapter
import com.shoppinggate.Model.AllProducts_Response
import com.shoppinggate.Model.Slider_DetailsProduct

import com.shoppinggate.R
import com.shoppinggate.ViewModel.Slider_DetailsProduct_ViewModel
import kotlinx.android.synthetic.main.activity_details__product.*
import kotlinx.android.synthetic.main.activity_details__product.view.*
import kotlinx.android.synthetic.main.activity_details__product.view.viewPager
import java.util.*
import androidx.lifecycle.Observer

/**
 * A simple [Fragment] subclass.
 */
class Details_Product : Fragment() {
    lateinit var details: AllProducts_Response.AllProducts_Model
    lateinit var UserToken: String
    private lateinit var DataSaver: SharedPreferences
    lateinit var Product_Id:String
    lateinit var root: View
    var swipeTimer: Timer?=null
    private var currentPage = 0
    private var NUM_PAGES = 0
    val handler = Handler()
    val Update = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0
        }
        root.viewPager!!.setCurrentItem(currentPage++, true)
    }
    lateinit var DeviceLang:String
    var bundle= Bundle()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.activity_details__product, container, false)

       getData()
       Language()
        getSlider()

        return root
    }



    fun getData(){
        bundle = this.arguments!!
        details = bundle.getParcelable("ProductItem")!!
        Product_Id=details.id.toString()
        root.T_Description.text=details.description
        root.Title.text=details.title
        root.small_discrption.text=details.shortDescription
        if (details.salePrice!=null) {
            if (details.salePrice.equals("0")) {
                root.T_Price.text =  details.priceGeneral
            } else {
                root.T_Price.text = details.salePrice
                root.T_OrignalPrice.text = details.priceGeneral
                root.T_OrignalPrice.setPaintFlags(root.T_OrignalPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            }
        }
        else {
            root.T_Price.text = details.priceGeneral
        }

    }


    fun getSlider(){
        var SliderHome: Slider_DetailsProduct_ViewModel = ViewModelProviders.of(this)[Slider_DetailsProduct_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            SliderHome.getData(Product_Id,DeviceLang, it).observe(this, Observer<Slider_DetailsProduct> { loginmodel ->
                if(loginmodel!=null) {
                    root.viewPager!!.adapter = this.context?.let { it1 ->
                        SliderProducts_Adapter(
                            it1,
                            loginmodel.data as ArrayList<Slider_DetailsProduct.Data>
                        )
                    }
                    root.view_pager_circle_indicator.setViewPager(viewPager)

                    NUM_PAGES = loginmodel.data!!.size
                    swipeTimer = Timer()
                    swipeTimer!!.schedule(object : TimerTask() {
                        override fun run() {
                            handler.post(Update)
                        }
                    }, 3000, 3000)
                }
            })
        }
    }

    fun Language() {
        DeviceLang = Locale.getDefault().language
    }
}

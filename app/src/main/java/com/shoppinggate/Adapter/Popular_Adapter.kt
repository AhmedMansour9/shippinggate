package com.shoppinggate.Adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoppinggate.Model.AllProducts_Response
import com.shoppinggate.R

class Popular_Adapter (context: Context, val userList: List<AllProducts_Response.AllProducts_Model>)
    : RecyclerView.Adapter<Popular_Adapter.ViewHolder>() {
    //this method is returning the view for each item in the list
//    lateinit var ProductsDetails: ProductDetails_View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Popular_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_popularhome, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Popular_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
//        holder.itemView.setOnClickListener(){
//            ProductsDetails.Details(userList.get(position))
//        }
    }
//    fun onClick(ProductsDetail: ProductDetails_View){
//        this.ProductsDetails=ProductsDetail
//    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context =itemView.context

        fun bindItems(dataModel: AllProducts_Response.AllProducts_Model) {
            val title = itemView.findViewById(R.id.Title) as TextView
            val img = itemView.findViewById(R.id.img_popular) as ImageView
            val price = itemView.findViewById(R.id.T_Price) as TextView
            val OrignalPrice = itemView.findViewById(R.id.T_OrignalPrice) as TextView
            title.text = dataModel.title
//             pricee = Math.round(dataModel.salePrice.toDouble())
//            val Price:Int=pricee.toInt()
            if (dataModel.salePrice!=null) {
                if (dataModel.salePrice.equals("0")) {
                    price.text = dataModel.priceGeneral
                } else {
                    price.text =  dataModel.salePrice
                    OrignalPrice.text = dataModel.priceGeneral
                    OrignalPrice.setPaintFlags(OrignalPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                }
            }
            else {
                price.text = dataModel.priceGeneral
            }
            Glide.with(context)
                .load("http://creativityvein.com" + dataModel.image).into(img)

        }
    }
}
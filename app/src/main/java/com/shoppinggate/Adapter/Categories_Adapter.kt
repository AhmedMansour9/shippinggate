package com.shoppinggate.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoppinggate.Model.Categories_Response
import com.shoppinggate.R
import com.shoppinggate.View.ProductById_View
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_categoryhome.view.*

class Categories_Adapter (context: Context, val userList: List<Categories_Response.CategoriesDetails>)
    : RecyclerView.Adapter<Categories_Adapter.ViewHolder>() {
    lateinit var productbyid: ProductById_View
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Categories_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_categoryhome, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Categories_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.setOnClickListener(){
           this.productbyid.Id(userList.get(position))
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }
    fun onClick(productI:ProductById_View){
        this.productbyid=productI
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context

        fun bindItems(dataModel: Categories_Response.CategoriesDetails) {
            val img = itemView.findViewById(R.id.image_Category) as CircleImageView
            itemView.T_Name.text=dataModel.title
            Glide.with(context)
                .load("http://creativityvein.com" + dataModel.image).fitCenter()
                .into(img)

        }
    }
}
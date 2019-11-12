package com.shoppinggate.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shoppinggate.R

class Sorts_Adapter  (context: Context, val userList: List<String>)
    : RecyclerView.Adapter<Sorts_Adapter.ViewHolder>() {
    var row_index: Int = -2
    var context: Context = context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Sorts_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_sort, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Sorts_Adapter.ViewHolder, position: Int) {
        holder.T_Title.text = userList.get(position)


    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context
        var T_Title = itemView.findViewById(R.id.title) as TextView


    }
}
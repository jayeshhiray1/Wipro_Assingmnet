package com.wipro.codingexcercise.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wipro.codingexcercise.R
import com.wipro.codingexcercise.model.Row
import com.wipro.codingexcercise.ui.mvvm.controller.FactClickListener

/**
 * @author JA20049996
 * Recycler adapter to set the data to the list
 */

class FactsAdapter( val context:Context,private val listData: ArrayList<Row>, private val factlistener: FactClickListener) : RecyclerView.Adapter<FactViewHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): FactViewHolder {
        return FactViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_fact, container, false))
    }

    override fun onBindViewHolder(viewholder: FactViewHolder, position: Int) {
        val rows = listData[position]
        viewholder.setData(rows)
        viewholder.itemView.setOnClickListener {
            factlistener.onFactClicklistener(rows)
        }
    }
    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return listData.size
    }
}


package com.wipro.codingexcercise.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wipro.codingexcercise.R
import com.wipro.codingexcercise.adapter.ViewHolders.FactViewHolder
import com.wipro.codingexcercise.model.Rows
import com.wipro.codingexcercise.ui.FactDetailsDialogFragment
import com.wipro.codingexcercise.utils.Constants

/**
 * @author JA20049996
 * Recycler adapter to set the data to the list
 */

class FactsAdapter(val listData: ArrayList<Rows>, val context: Context) : RecyclerView.Adapter<FactViewHolder>() {


    var factDetailsDialogFragment: FactDetailsDialogFragment = FactDetailsDialogFragment()

    var fragmentManager: FragmentManager? = (context as AppCompatActivity).supportFragmentManager

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): FactViewHolder {
        return FactViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_fact, container, false))
    }

    override fun onBindViewHolder(viewholder: FactViewHolder, position: Int) {
        val rows = listData.get(position)
        viewholder.setData(rows)

        viewholder.itemView.setOnClickListener {
            var bundle = Bundle()
            bundle?.putParcelable(Constants.BundleKeys.KEY_FACT, rows)

            factDetailsDialogFragment.arguments = bundle
            factDetailsDialogFragment?.show(fragmentManager?.beginTransaction(), factDetailsDialogFragment?.tag)
        }

    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return listData.size
    }

}


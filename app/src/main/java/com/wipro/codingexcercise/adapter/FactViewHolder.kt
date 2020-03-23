package com.wipro.codingexcercise.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wipro.codingexcercise.R
import com.wipro.codingexcercise.model.Row
import kotlinx.android.synthetic.main.itemview_fact.view.*

/**
 * @author JA20049996
 * Used to Display data on screen.
 */

class FactViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun setData(data: Row) {
        view.textViewTitle.text = data.title
        view.textviewDescription.text = data.description

        //Load images in list
        Glide.with(view.context)
                .load(data.getImageUrl())
                .apply(RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.error_img)
                        .fitCenter()
                        .override(100))
                .into(view.imageViewFact)
    }
}
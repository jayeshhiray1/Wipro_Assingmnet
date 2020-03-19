package com.wipro.codingexcercise.adapter.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.itemview_fact.view.*
import com.wipro.codingexcercise.R
import com.wipro.codingexcercise.model.Rows

/**
 * @author JA20049996
 * Used to Display data on screen.
 */

class FactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvTitle = view.textViewTitle!!
    val tvDesc = view.textviewDescription!!
    val imageviewFact = view.imageViewFact!!
    val imagviewArrow = view.imageViewArrow!!


    fun setData(data: Rows) {
        tvTitle.text = data.title
        tvDesc.text = data.description
        tvTitle.text = data.title

//Load images in list
        Glide.with(imageviewFact.context)
                .load(data.imageHref)
                .apply(RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.error_img)
                        .fitCenter()
                        .override(100))
                .into(imageviewFact)
    }

}
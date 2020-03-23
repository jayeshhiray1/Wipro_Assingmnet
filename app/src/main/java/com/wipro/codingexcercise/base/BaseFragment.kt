package com.wipro.codingexcercise.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatTextView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar

import com.wipro.codingexcercise.R

/**
 * @author JA20049996
 * THis is the base class for all the fragment where all the common codes are writen here
 * like showing error and showing error message
 */

abstract class BaseFragment : Fragment() {

    private var mProgressBar: ProgressBar? = null
    private var tvError: AppCompatTextView? = null
    private var mLayoutId: Int = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }


    //ui initialization to show progressbar and error message for all the classes

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_base, container, false)
        mProgressBar = view.findViewById<View>(R.id.progressbarFragment) as ProgressBar
        tvError = view.findViewById<View>(R.id.tvError) as AppCompatTextView
        val fragmentLayoutContainer = view.findViewById<View>(R.id.frameLayooutDialogFragment) as FrameLayout
        inflater.inflate(mLayoutId, fragmentLayoutContainer)

        return view

    }


    protected abstract fun init()

    protected fun setLayout(layoutId: Int) {
        mLayoutId = layoutId
    }

    /**
     * Hide the common progressbar
     */
    protected fun hideProgressBar() {
        mProgressBar?.let {
            mProgressBar!!.visibility = View.GONE
        }
    }

    /**
     * show the common progressbar
     */
    protected fun showProgressBar() {
        mProgressBar?.let {
            mProgressBar!!.visibility = View.VISIBLE
        }
    }

    /**
     * this function is used show error
     */
    protected fun showBaseError(message: String) {
        tvError?.let {
            tvError!!.visibility = View.VISIBLE
            tvError?.setText(message)

            if (TextUtils.isEmpty(message)) {
                tvError!!.visibility = View.GONE
            }

        }
    }
}

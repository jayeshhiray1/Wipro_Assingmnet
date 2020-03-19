package com.wipro.codingexcercise.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.dialogfragment_factdetails.*
import com.wipro.codingexcercise.R
import com.wipro.codingexcercise.model.Rows
import com.wipro.codingexcercise.utils.Constants
import java.util.*



/**
 * @author JA20049996
 * This class to show the fact details
 */
class FactDetailsDialogFragment : DialogFragment() {


    private var mProgressBar: ProgressBar? = null
    private var imageViewBack: AppCompatImageView? = null
    private var textViewTitle: AppCompatTextView? = null
    private var mLayoutId: Int = 0
    protected var mRequestTag: String? = null
    private var constraintLayoutRoot: ConstraintLayout? = null
    var stringList: List<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColorIfPossible()
    }

    fun setStatusBarColorIfPossible() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity!!.window.statusBarColor = ContextCompat.getColor(activity!!, R.color.colorPrimaryDark)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialogfragment_factdetails, container, false)
        constraintLayoutRoot = view.findViewById(R.id.constraintLayoutRoot)
        imageViewBack = view.findViewById<View>(R.id.imageViewBack) as AppCompatImageView
        textViewTitle = view.findViewById<View>(R.id.textViewTitle) as AppCompatTextView


        imageViewBack!!.setOnClickListener { dismissAllowingStateLoss() }

        textViewTitle?.setText("Fact Details")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        val rows: Rows = bundle?.getParcelable(Constants.BundleKeys.KEY_FACT) as Rows

        rows?.let {
            appCompatTextView?.text = rows.title
            testViewDesc?.text = rows.description


            Glide.with(activity as AppCompatActivity)
                    .load(rows.imageHref)
                    .apply(RequestOptions()
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.drawable.error_img)
                            .circleCrop()
                            .fitCenter()
                            .override(100))
                            .into(imageViewFact)
        }

    }


    protected fun hideProgressBar() {
        if (mProgressBar != null)
            mProgressBar!!.visibility = View.GONE
    }

    protected fun showProgressBar() {
        if (mProgressBar != null)
            mProgressBar!!.visibility = View.VISIBLE
    }


    protected fun setTitle(title: String) {
        textViewTitle!!.text = title
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setWindowAnimations(
                    R.style.styleDialogFragment)
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                dialog?.window?.setStatusBarColor(ContextCompat.getColor(activity as AppCompatActivity, R.color.colorPrimaryDark));
            }

        }
    }


}

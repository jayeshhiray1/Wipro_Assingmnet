package com.wipro.codingexcercise.ui.mvvm.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wipro.codingexcercise.R
import com.wipro.codingexcercise.adapter.FactsAdapter
import com.wipro.codingexcercise.base.BaseFragment
import com.wipro.codingexcercise.model.Row
import com.wipro.codingexcercise.ui.FactDetailsDialogFragment
import com.wipro.codingexcercise.ui.mvvm.controller.FactClickListener
import com.wipro.codingexcercise.ui.mvvm.viewmodel.FactViewModel
import com.wipro.codingexcercise.utils.CommonUtility
import com.wipro.codingexcercise.utils.Constants
import java.util.*

/**
 * Class to show list of facts
 */
class MvvmFactFragment : BaseFragment(), FactClickListener {

    private val factDetailsDialogFragment:FactDetailsDialogFragment by lazy {
        FactDetailsDialogFragment()
    }

    private lateinit var factAdapter: FactsAdapter
    private var listData = ArrayList<Row>()
    private lateinit var factViewModel: FactViewModel

    // UI widgets declaration
    private lateinit var recyclerViewFact: RecyclerView
    //Pull to trefresh
    private lateinit var pullToRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        setLayout(R.layout.fragment_fact)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewFact = view.findViewById(R.id.recyclerViewFact)
        pullToRefresh = view.findViewById(R.id.pullToRefresh)

        init()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        factViewModel = ViewModelProviders.of(this).get(FactViewModel::class.java)
    }

    //initialization of adapter and other ui related things

    public override fun init() {
        recyclerViewFact.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        factAdapter = FactsAdapter(Objects.requireNonNull<FragmentActivity>(activity),listData,this)
        recyclerViewFact.adapter = factAdapter


        pullToRefresh.setOnRefreshListener {
            hideProgressBar()
            val bool = factViewModel.getFactListFromController()
            if (!bool) {
                pullToRefresh.isRefreshing = false
            }
        }

        if (CommonUtility.isInternetAvailable(activity as AppCompatActivity) && listData.size == 0) {
            showProgressBar()
        } else {
            hideProgressBar()
        }

        factViewModel.setContextAndFetchData(activity as Context)

        if(listData.size == 0) {
            with(factViewModel) {
                subscribeFactList(this)
                subscribeToErrorMessage(this)
                subscribeTitle(this)
            }
        }
        else{
            updateRecyclerView(listData)
        }

        lifecycle.addObserver(factViewModel)

        if (CommonUtility.isInternetAvailable(activity as AppCompatActivity)) {
            showProgressBar()
        } else {
            hideProgressBar()
        }

    }

    /**
     *
     * @param factViewModel
     *
     * Observable callback from view model
     */
    private fun subscribeFactList(factViewModel: FactViewModel) {
        factViewModel.getfactListObservable().observe(this, Observer { rows ->
            updateRecyclerView(rows)
        })
    }

    fun updateRecyclerView(rows: List<Row>?) {
        pullToRefresh.isRefreshing = false
        listData.clear()
        rows?.let { listData.addAll(it) }
        factAdapter.notifyDataSetChanged()
        hideProgressBar()
        showBaseError("")
    }

    private fun subscribeTitle(factViewModel: FactViewModel) {
        factViewModel.getTitleObservable().observe(this, Observer { title ->
            activity?.title = title
        })
    }

    //This method observe the changes in the error message
    private fun subscribeToErrorMessage(factViewModel: FactViewModel) {
        factViewModel.getErrorMessage().observe(this, Observer { errorMessage ->
            showBaseError(errorMessage as String)
            hideProgressBar()
        })
    }

    companion object {

        val instance: MvvmFactFragment
            get() = MvvmFactFragment()
    }

    override fun onFactClicklistener(rows: Row) {
        if (!factDetailsDialogFragment.isAdded) {
            val bundle = Bundle()
            bundle.putParcelable(Constants.BundleKeys.KEY_FACT, rows)
            factDetailsDialogFragment.arguments = bundle
            factDetailsDialogFragment.show(fragmentManager, factDetailsDialogFragment.tag)
        }
    }
}
package com.wipro.codingexcercise.ui.mvvm.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wipro.codingexcercise.R
import com.wipro.codingexcercise.adapter.FactsAdapter
import com.wipro.codingexcercise.base.BaseFragment
import com.wipro.codingexcercise.model.Rows
import com.wipro.codingexcercise.ui.mvvm.viewmodel.FactViewModel
import com.wipro.codingexcercise.utils.CommonUtility
import java.util.*

/**
 * Class to show list of facts
 */
class MvvmFactFragment : BaseFragment()  {

    lateinit var factAdapter: FactsAdapter
    var listData = ArrayList<Rows>()
    lateinit var factViewModel: FactViewModel

    // UI widgets declaration
    lateinit var recyclerViewFact: RecyclerView
    //Pull to trefresh
    lateinit var pullToRefresh: SwipeRefreshLayout

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
        setOnClickListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        factViewModel = ViewModelProviders.of(this).get(FactViewModel::class.java)

        if (CommonUtility.isInternetAvailable(Objects.requireNonNull<FragmentActivity>(activity))) {
            showProgressBar()
        } else {
            hideProgressBar()
        }

        factViewModel.setContextAndFetchData(activity as Context)

        with(factViewModel){
            subscribeFactList(this)
            subscribeToErrorMessage(this)
            subscribeTitle(this)
        }

        lifecycle.addObserver(factViewModel)

        if (CommonUtility.isInternetAvailable(Objects.requireNonNull<FragmentActivity>(activity))) {
            showProgressBar()
        } else {
            hideProgressBar()
        }

    }

    //initialization of adapter and other ui related things

    public override fun init() {
        recyclerViewFact.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        factAdapter = FactsAdapter(listData, Objects.requireNonNull<FragmentActivity>(activity))
        recyclerViewFact.adapter = factAdapter


        pullToRefresh.setOnRefreshListener {

            Toast.makeText(activity, getString(R.string.loading),
                    Toast.LENGTH_SHORT).show()

            val bool = factViewModel.getFactListFromController()
            if (!bool) {
                pullToRefresh.isRefreshing = false
            }
        }
    }

    public override fun setOnClickListener() {

    }

    /**
     *
     * @param factViewModel
     *
     * Observable callback from view model
     */
    private fun subscribeFactList(factViewModel: FactViewModel) {
        factViewModel.getfactListObservable().observe(this, Observer { rows ->
            pullToRefresh.isRefreshing = false
            listData.clear()
            assert(rows != null)
            listData.addAll(rows!!)
            factAdapter.notifyDataSetChanged()
            hideProgressBar()
            showBaseError("")
        })
    }

    private fun subscribeTitle(factViewModel: FactViewModel) {
        factViewModel.getTitleObservable().observe(this, Observer { title ->
           activity!!.setTitle(title)
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
}

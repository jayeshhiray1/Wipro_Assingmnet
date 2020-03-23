package com.wipro.codingexcercise.ui.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.wipro.codingexcercise.model.Row
import com.wipro.codingexcercise.ui.mvvm.controller.FactController
import com.wipro.codingexcercise.ui.mvvm.controller.ViewModelInterface
import com.wipro.codingexcercise.utils.CommonUtility
import java.util.*

/**
 * @author JA20049996
 * View model Class
 */
open class FactViewModel(application:Application) : AndroidViewModel(application),
        LifecycleObserver, ViewModelInterface {

    // observable FactList
    private val factList: MutableLiveData<List<Row>> = MutableLiveData()

    lateinit var mContext: Context

    // observable error message
    private var message: MutableLiveData<String> = MutableLiveData()
    private var title: MutableLiveData<String> = MutableLiveData()

    // model to get the data from the server
    var factController: FactController = FactController(application,this)

    /**
     * this method is observer by the View and
     * update the view as per change in the List<Rows>
     * @return
     */
    fun getfactListObservable(): LiveData<List<Row>> {
        return factList
    }

    fun getTitleObservable(): LiveData<String> {
        return title
    }

    /**
     * Observable method to observe by the View
     * to show error
     */
    override fun setMessage(msg: String) {
        this.message.value = msg
    }

    /**
     * Method to get data from the server in the
     * controller class
     */
    fun getFactListFromController(): Boolean {
        if (CommonUtility.isInternetAvailable(mContext as AppCompatActivity)) {
            factController.getAllFacts()
        } else {
            setMessage("No Internet Connection")
        }
        return CommonUtility.isInternetAvailable(mContext as AppCompatActivity)
    }

    /**
     * call back method we get from the controller after
     * successful fetching data from the server
     */
    override fun setFacts(title: String, rowsList: ArrayList<Row>) {

        this.title.value = title

        if (!CommonUtility.isInternetAvailable(mContext as AppCompatActivity)) {
            setMessage("No Internet Connection")
        } else if ((rowsList != null && rowsList.size == 0) || TextUtils.isEmpty(title) || rowsList == null) {
            setMessage("Server Error, no data found")
        } else {
            factList.value  =  rowsList.filter {
                !TextUtils.isEmpty(it.title) && !TextUtils.isEmpty(it.description)
            }
        }
    }

    companion object {
        private val TAG = "FactViewModel"
    }

    public fun setContextAndFetchData(context: Context) {
        this.mContext = context
        getFactListFromController()
    }

    fun getErrorMessage(): LiveData<String> {
        return message
    }

    override fun getFacts() {

    }

    override fun showProgress() {

    }

    override fun showError(message: String) {

    }

    override fun hideProgress() {

    }
}
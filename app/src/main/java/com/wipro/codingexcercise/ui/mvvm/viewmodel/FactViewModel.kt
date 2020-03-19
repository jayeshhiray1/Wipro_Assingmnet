package com.wipro.codingexcercise.ui.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.wipro.codingexcercise.model.Rows
import com.wipro.codingexcercise.ui.mvvm.controller.FactController
import com.wipro.codingexcercise.ui.mvvm.controller.ViewModelInterface
import com.wipro.codingexcercise.utils.CommonUtility
import java.util.*

/**
 * @author JA20049996
 * View model Class
 */
open class FactViewModel(application: Application) : AndroidViewModel(application),
        LifecycleObserver, ViewModelInterface {

    // observable FactList
    private val factList: MutableLiveData<List<Rows>>

    lateinit var mContext: Context

    // observable error message
    private var message: MutableLiveData<String>
    private var title: MutableLiveData<String>

    // model to get the data from the server
    var factController: FactController

    // initializer block in kotlin
    init {
        factList = MutableLiveData()
        message = MutableLiveData()
        title = MutableLiveData()
        factController = FactController(application, this)
    }

    /**
     * this method is observer by the View and
     * update the view as per change in the List<Rows>
     * @return
     */
    fun getfactListObservable(): LiveData<List<Rows>> {
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
        if (CommonUtility.isInternetAvailable(mContext)) {
            factController.getAllFacts()
        } else {
            setMessage("No Internet Connection")
        }
        return CommonUtility.isInternetAvailable(mContext)
    }

    /**
     * call back method we get from the controller after
     * successful fetching data from the server
     */
    override fun setFacts(title: String, rowsList: ArrayList<Rows>) {

        this.title.value = title

        if (!CommonUtility.isInternetAvailable(mContext)) {
            setMessage("No Internet Connection")
        } else if ((rowsList != null && rowsList.size == 0) || TextUtils.isEmpty(title) || rowsList == null) {
            setMessage("Server Error, no data found")
        } else {
            factList.value = rowsList
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    internal fun any() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    internal fun stop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    internal fun onCreate() {
        Log.i(TAG, "onCreate: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun onStart() {
        Log.i(TAG, "onStart: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    internal fun onStop() {
        Log.i(TAG, "onStop: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    internal fun onPause() {
        Log.i(TAG, "onPause: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    internal fun onResume() {
        Log.i(TAG, "onResume: ")
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
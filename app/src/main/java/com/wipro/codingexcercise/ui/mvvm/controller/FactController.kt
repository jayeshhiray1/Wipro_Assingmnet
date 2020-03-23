package com.wipro.codingexcercise.ui.mvvm.controller

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.wipro.codingexcercise.model.FactData
import com.wipro.codingexcercise.model.Row
import com.wipro.codingexcercise.utils.network.APIInterface
import com.wipro.codingexcercise.utils.network.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


/**
 * @author JA20049996
 */

open class FactController(context: Context, private val viewModelInterface: ViewModelInterface) {

    private val apiInterface: APIInterface = RetrofitHelper.getInstance(context).create(APIInterface::class.java)

    /**
     * API call to fetch all the facts from the URL given
     */
    fun getAllFacts() {
        val data = ArrayList<Row>()

        apiInterface.getALlFacts().enqueue(object : Callback<FactData> {
            override fun onResponse(call: Call<FactData>, response: Response<FactData>) {

                val factsDto = response.body()

                if (factsDto != null &&
                        factsDto.rows != null &&
                        factsDto.rows!!.size > 0) {
                    /**
                     * Filter out the items which have empty title and description
                     */
                    val filteredMap:List<Row> = factsDto.rows!!.filter {
                        !TextUtils.isEmpty(it?.description) &&  !TextUtils.isEmpty(it?.title)
                    } as List<Row>
                    /**
                     * passing data to Viewmodel
                     */
                    factsDto.title?.let { viewModelInterface.setFacts(it, factsDto.rows as ArrayList<Row>) }
                } else {
                    viewModelInterface.setMessage("No Data Found")
                }
            }

            override fun onFailure(call: Call<FactData>, t: Throwable) {
                Log.i(TAG, "onFailure: $call")
                if (call == null) {
                    viewModelInterface.setMessage("Error getting response")
                }
            }

        })
    }

    companion object {

        private val TAG = "FactInteractor"
    }
}

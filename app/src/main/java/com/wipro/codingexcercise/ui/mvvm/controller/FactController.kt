package com.wipro.codingexcercise.ui.mvvm.controller

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.wipro.codingexcercise.model.FactsDto
import com.wipro.codingexcercise.model.Rows
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
    private val apiInterface: APIInterface

    init {
        apiInterface = RetrofitHelper.getInstance(context).create(APIInterface::class.java)
    }

    /**
     * API call to fetch all the facts from the URL given
     */
    fun getAllFacts() {
        val data = ArrayList<Rows>()

        apiInterface.getALlFacts().enqueue(object : Callback<FactsDto> {
            override fun onResponse(call: Call<FactsDto>, response: Response<FactsDto>) {

                val factsDto = response.body()

                if (factsDto != null &&
                        factsDto.rows != null &&
                        factsDto.rows.size > 0) {
                    /**
                     * Filter out the items which have empty title and description
                     */
                    val filteredMap:List<Rows> = factsDto.rows.filter {
                        !TextUtils.isEmpty(it.description) &&  !TextUtils.isEmpty(it.title)
                    }
                    /**
                     * passing data to Viewmodel
                     */
                    viewModelInterface.setFacts(factsDto.title, factsDto.rows as ArrayList<Rows>)
                } else {
                    viewModelInterface.setMessage("No Data Found")
                }
            }

            override fun onFailure(call: Call<FactsDto>, t: Throwable) {
                Log.i(TAG, "onFailure: $call")
                viewModelInterface.setMessage("Error getting response")
            }

        })
    }

    companion object {

        private val TAG = "FactInteractor"
    }
}

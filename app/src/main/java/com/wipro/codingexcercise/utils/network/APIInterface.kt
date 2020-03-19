package com.wipro.codingexcercise.utils.network

import com.wipro.codingexcercise.model.FactsDto
import retrofit2.Call
import retrofit2.http.GET

/**
 * @author JA20049996
 * Retrofit service
 * to define the END points and the response data
 */
interface APIInterface {
    //  https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json
    @GET("facts.json")
    abstract fun getALlFacts(): Call<FactsDto>
}
package com.wipro.codingexcercise.utils.network

import android.content.Context
import com.wipro.codingexcercise.app.MyApplication
import com.wipro.codingexcercise.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author JA20049996
 * UTILITY class for retrofit manager class
 *
 * we can add interecepror and cache management and rety policy add here
 */
class RetrofitHelper {

    companion object {

        public fun getInstance(context: Context = MyApplication.getInstance()): Retrofit {


            val okHttpClient = OkHttpClient.Builder()
                    //  .cache(cache)
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.UrlConstants.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()

            return retrofit
        }
    }
}
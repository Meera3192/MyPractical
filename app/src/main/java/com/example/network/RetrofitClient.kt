package com.example.network

import com.example.mypractical.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Meera
 * Date : 9-08-2019.
 * Package_Name : com.example.network
 * Class_Name : RetrofitClient
 * Description : This is public class for get RetrofitService instance.
 */
object RetrofitClient
{
    var retrofit: Retrofit? = null
    var logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val okhttpclient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    // Use this method for genrating implementation of RetrofitService interface.
    private fun getRetrofitData(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_GIPHY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpclient)
                .build()
        }
        return retrofit
    }

    // Get RetrofitService instance.
    val retrofitService = getRetrofitData()?.create(RetrofitService::class.java)
}


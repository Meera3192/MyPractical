package com.example.network

import com.example.search.SearchResponceModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Meera
 * Date : 9-08-2019.
 * Package_Name : com.example.network
 * Interface_Name : RetrofitService
 * Description : Interface using special retrofit annotations to encode details about the parameters and request method.
 */
interface RetrofitService
{

    /***** https://api.giphy.com/v1/gifs/search?api_key=g54MLmPUfsjNFs5OyNTeGnAQnuzOzI5Q&q=game%20of%20thrones&limit=25&offset=0&rating=G&lang=en *****/
    @GET("v1/gifs/search")
    fun getSearchData(
        @Query("api_key") api_Key : String,
        @Query("q") searchText : String,
        @Query("limit") itemLimite : Int,
        @Query("offset") offset :Int,
        @Query("rating") rating : String,
        @Query("lang") lang : String
    ) : Call<SearchResponceModel>



}
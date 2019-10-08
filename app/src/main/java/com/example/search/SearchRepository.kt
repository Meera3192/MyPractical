package com.example.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.network.RetrofitClient
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Meera
 * Date : 9-08-2019.
 * Package_Name : com.example.search
 * Class_Name : SearchRepository
 * Description : This class is used for access of Multiple DataSources.
 * Repository is not part of architecture component.
 * here we call the API and pass Data in SearchViewModel.
 */
class SearchRepository
{
    val TAG = "SearchRepository"
    val ITEMLIMITE =40
    val OFFSET = 0
    val RATING ="G"
    val LANG = "en"
    val API_KEY = "g54MLmPUfsjNFs5OyNTeGnAQnuzOzI5Q"

    /**
     * This method use for API call and return list of Search data
     */
    fun getSearchData(searchString: String): LiveData<SearchResponceModel> {
        var list = MutableLiveData<SearchResponceModel>()

        val call = RetrofitClient.retrofitService?.getSearchData(
            API_KEY,searchString,ITEMLIMITE,OFFSET,RATING,LANG)
        call?.enqueue(object : retrofit2.Callback<SearchResponceModel> {
            override fun onFailure(call: Call<SearchResponceModel>, t: Throwable) {
                Log.d(TAG, t.message)
            }

            override fun onResponse(call: Call<SearchResponceModel>, response: Response<SearchResponceModel>) {
                if (response.isSuccessful) {
                    list.value = response.body()

                }
            }
        })
        return list
    }

}
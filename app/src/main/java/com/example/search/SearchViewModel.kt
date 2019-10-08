package com.example.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
/**
 * Created by Meera
 * Date : 9-08-2019.
 * Package_Name : com.example.search
 * Class_Name : SearchViewModel
 * Description : ViewModel class used for holding data for Activity/Fragment.
 * it also communication center between the Repository and the UI.
 * here we call the repository method for calling Api and return data to the HomeActivity.
 */
class SearchViewModel : ViewModel()
{
    /**
     * Get instance of SearchRepository and call the method of SerachRepository class
     */
    fun getSearchData(searchString: String): LiveData<SearchResponceModel> {
        val SearchRepository = SearchRepository()
        return  SearchRepository.getSearchData(searchString)
    }
}
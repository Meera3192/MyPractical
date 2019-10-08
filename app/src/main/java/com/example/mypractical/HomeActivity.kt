package com.example.mypractical

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import android.os.Bundle
import android.view.Menu
import android.view.View

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.objectbox.ObjectBox
import com.example.objectbox.UserSearchRecord
import com.example.search.Search
import com.example.search.SearchAdapter
import com.example.search.SearchViewModel
import com.example.search.SearchResponceModel
import io.objectbox.Box

/**
 * Created by Meera
 * Date : 9-08-2019
 * Package_Name : com.example.mypractical
 * Class_Name : HomeActivity
 * Description : This is main class of project, in this we get serach text, initialize viewmodel instance for calling
 * api and bind recyclerview.
 */
class HomeActivity : AppCompatActivity() {

    @BindView(R.id.rvSearch)
    lateinit var rvSearch : RecyclerView

    lateinit var searchViewModel: SearchViewModel
    lateinit var searchBox: Box<UserSearchRecord>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initialize()
    }

    /**
     * This method used for initialize variables and objects.
     */
    private fun initialize() {
        searchBox = ObjectBox.get()?.boxFor(UserSearchRecord::class.java)!!
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    /**
     * Used for get MenuItem and parform operation on menuItem.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        // Optional: if we want to expand SearchView from icon to edittext view
        searchItem?.expandActionView()
        val searchView = searchItem?.actionView as SearchView

        // Call Api on text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchString: String?): Boolean {
                fetchDataFromServer(searchString.toString())
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * This method used for fetching data from server according MVVM Architecture
     */
    private fun fetchDataFromServer(searchStr: String) {
        searchViewModel.getSearchData(searchStr).observe(this, object : Observer<SearchResponceModel> {
            override fun onChanged(data: SearchResponceModel?) {
                if (data != null) {
                    var list = ArrayList<Search>()

                    for (data in data.data) {
                        val show_Data = Search(
                            data.id,
                            data.images.original_mp4.mp4,
                            data.images.`480w_still`.url
                        )
                        list.add(show_Data)
                    }

                    bindList(list)
                } else {
                    Toast.makeText(this@HomeActivity, getString(R.string.no_match), Toast.LENGTH_LONG).show();
                }
            }
        })
    }

    /**
     * Using this method bind list in recyclerview
     */
    private fun bindList(list: ArrayList<Search>) {
        rvSearch.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        var adapter = SearchAdapter(list)
        rvSearch.adapter = adapter
        adapter.setItemClickListener(object : SearchAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {

                if (!list.isNullOrEmpty()) {

                    // Add Search_Data in ObjectBox
                    val searchRecord = UserSearchRecord()
                    searchRecord.image_url = list[position].image_url
                    searchRecord.original_url = list[position].original_url
                    searchBox.put(searchRecord)

                    // display total record of ObjectBox
                    val data = searchBox.all
                    Toast.makeText(this@HomeActivity,getString(R.string.total_video,data.size.toString()),Toast.LENGTH_LONG).show()

                    // Play Video in Player
                    val intent = Intent(this@HomeActivity, Player::class.java)
                    intent.putExtra("EXTRA_VIDEOURL", list[position].original_url)
                    startActivity(intent)
                }
            }
        })

    }
}

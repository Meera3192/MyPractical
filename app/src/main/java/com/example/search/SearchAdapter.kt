package com.example.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mypractical.R
import com.example.mypractical.databinding.RowSearchItemBinding

/**
 * Created by Meera
 * Date : 9-08-2019.
 * Package_Name : com.example.search
 * Class_Name : SearchAdapter
 * Description : This class work as subclass of RecyclerView.Adapter responsible for providing views
 * that represent items in a data set.
 */

/**
 * Use this method for binding image
 */
@BindingAdapter("Image_Url")
fun loadImage(view: AppCompatImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.download)
        .apply(RequestOptions())
        .into(view)
}

class SearchAdapter(list: ArrayList<Search>) : RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHolder>() {

    var mList: ArrayList<Search>
    var onItemClickListener: ItemClickListener? = null

    init {
        mList = list
    }

    /**
     * Use this method for passing view in viewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.SearchAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_search_item, parent, false)
        return SearchAdapterViewHolder(view)
    }

    /**
     * This class is used to cache the view objects in order to save memory.
     */
    class SearchAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var xmlBinding: RowSearchItemBinding

        init {
            xmlBinding = DataBindingUtil.bind(itemView)!!
        }
    }

    override fun getItemCount(): Int = mList.size

    /**
     * This method used for bind view according position
     */
    override fun onBindViewHolder(holder: SearchAdapterViewHolder, position: Int) {
        if (holder != null) {
            holder.xmlBinding.search = mList[position]
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(it, position)
            }
        }
    }

    // Create interface for item listener
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setItemClickListener(clickListener: ItemClickListener) {
        onItemClickListener = clickListener
    }
}
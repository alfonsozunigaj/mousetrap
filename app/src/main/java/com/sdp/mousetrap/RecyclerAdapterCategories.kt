package com.sdp.mousetrap

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sdp.mousetrap.DB.Category
import com.sdp.mousetrap.DB.ViewHolderCategories

class RecyclerAdapterCategories : RecyclerView.Adapter<ViewHolderCategories>() {

    var categories: MutableList<Category>  = ArrayList()
    lateinit var fragmentDelegate: FragmentDelegate

    fun RecyclerAdapterCategories(categories : MutableList<Category>, fragmentDelegate: FragmentDelegate){
        this.categories = categories
        this.fragmentDelegate = fragmentDelegate
    }

    override fun onBindViewHolder(holder: ViewHolderCategories, position: Int) {
        val item = categories[position]
        holder.bind(item, fragmentDelegate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategories {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderCategories(layoutInflater.inflate(R.layout.item_category_list, parent, false))
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}
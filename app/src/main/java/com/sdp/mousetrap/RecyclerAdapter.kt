package com.sdp.mousetrap

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sdp.mousetrap.DB.Poll

class RecyclerAdapter : RecyclerView.Adapter<ViewHolder>() {

    var polls: MutableList<Poll>  = ArrayList()
    lateinit var fragmentDelegate: FragmentDelegate

    fun RecyclerAdapter(polls : MutableList<Poll>, fragmentDelegate: FragmentDelegate){
        this.polls = polls
        this.fragmentDelegate = fragmentDelegate
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = polls[position]
        holder.bind(item, fragmentDelegate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_poll_list, parent, false))
    }

    override fun getItemCount(): Int {
        return polls.size
    }
}
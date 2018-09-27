package com.sdp.mousetrap

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class RecyclerAdapter : RecyclerView.Adapter<ViewHolder>() {

    var polls: MutableList<Poll>  = ArrayList()
    lateinit var context: Context

    fun RecyclerAdapter(polls : MutableList<Poll>, context: Context){
        this.polls = polls
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = polls.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_poll_list, parent, false))
    }

    override fun getItemCount(): Int {
        return polls.size
    }
}
package com.sdp.mousetrap

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sdp.mousetrap.DB.Award

class SecondRecyclerAdapter : RecyclerView.Adapter<SecondViewHolder>() {

    var awards: MutableList<Award>  = ArrayList()
    lateinit var context: Context

    fun SecondRecyclerAdapter(awards : MutableList<Award>, context: Context){
        this.awards = awards
        this.context = context
    }

    override fun onBindViewHolder(holder: SecondViewHolder, position: Int) {
        val item = awards.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SecondViewHolder(layoutInflater.inflate(R.layout.item_award_list, parent, false))
    }

    override fun getItemCount(): Int {
        return awards.size
    }
}
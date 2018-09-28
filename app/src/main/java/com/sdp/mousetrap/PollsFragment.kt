package com.sdp.mousetrap


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*


class PollsFragment : Fragment() {

    lateinit var mRecyclerView : RecyclerView
    val mAdapter : RecyclerAdapter = RecyclerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view : View = inflater.inflate(R.layout.fragment_polls, container, false)
        setUpRecyclerView(view)
        return view
    }

    fun setUpRecyclerView(view: View){
        mRecyclerView = view.findViewById(R.id.rvPollList) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter.RecyclerAdapter(getPolls(), context)
        mRecyclerView.adapter = mAdapter
    }

    fun getPolls(): MutableList<Poll> {
        var polls:MutableList<Poll> = ArrayList()
        polls.add(Poll("Burger King", 12000, Date(2018, 5,12), true, 2, Date(2019, 5,7), 20, 500))
        polls.add(Poll("Nike", 25000, Date(2018, 8,12), true, 2, Date(2019, 5,12), 20, 200))
        polls.add(Poll("Microsoft", 6000, Date(2018, 7,12), true, 5, Date(2019, 5,13), 100, 190))
        polls.add(Poll("Santa Isabel", 7800, Date(2018, 5,12), true, 2, Date(2019, 5,5), 80, 250))
        polls.add(Poll("AWS", 18900, Date(2018, 12,12), true, 2, Date(2019, 9,12), 20, 200))
        polls.add(Poll("Paris", 15500, Date(2018, 1,12), true, 3, Date(2019, 2,12), 45, 200))
        polls.add(Poll("Universidad de los Andes", 10000, Date(2018, 5,12), true, 2, Date(2019, 5,12), 20, 200))
        polls.add(Poll("Reebook", 17450, Date(2018, 4,12), true, 10, Date(2019, 4,12), 75, 200))
        return polls
    }
}

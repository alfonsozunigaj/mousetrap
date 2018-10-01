package com.sdp.mousetrap


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AwardsFragment : Fragment() {

    lateinit var mRecyclerView : RecyclerView
    val mAdapter : SecondRecyclerAdapter = SecondRecyclerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view : View = inflater.inflate(R.layout.fragment_awards, container, false)
        setUpRecyclerView(view)
        return view
    }

    fun setUpRecyclerView(view: View) {
        mRecyclerView = view.findViewById(R.id.rvPollList) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(context, 2);
        mAdapter.SecondRecyclerAdapter(getAwards(), context)
        mRecyclerView.adapter = mAdapter
    }

    fun getAwards(): MutableList<Award> {
        var awards:MutableList<Award> = ArrayList()
        awards.add(Award("iPod Classic", 59990, "https://i1.wp.com/www.playsat.es/wp-content/uploads/2015/10/Reparar-iPod-Classic.png?fit=420%2C420&ssl=1"))
        return awards
    }
}

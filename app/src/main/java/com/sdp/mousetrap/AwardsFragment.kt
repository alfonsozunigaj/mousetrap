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
        mRecyclerView = view.findViewById(R.id.rvAwardList) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(context, 2)
        mAdapter.SecondRecyclerAdapter(getAwards(), context)
        mRecyclerView.adapter = mAdapter
    }

    fun getAwards(): MutableList<Award> {
        var awards:MutableList<Award> = ArrayList()
        awards.add(Award("iPod Classic", 59990, "https://www.bytetotal.com/sites/default/files/styles/nota_full/public/ipod-classic-2009.png?itok=YrV-fTFw"))
        awards.add(Award("Amazon Echo", 29990, "https://images.ctfassets.net/gdch8ejbyxg5/724b0zh2BU64QsAsQmYIOk/156f188bd06afa7ceddd76ebeb9d4ddd/Amazon_Dot_Front.png"))
        awards.add(Award("Chromecast", 15990, "https://c1-ebgames.eb-cdn.com.au/merchandising/images/packshots/6b8320e826e24ab99ce42127c4065ce0_Large.png"))
        return awards
    }
}

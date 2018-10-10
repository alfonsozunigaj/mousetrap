package com.sdp.mousetrap


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sdp.mousetrap.DB.Award
import org.json.JSONObject


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
        getAwards()
        //mAdapter.SecondRecyclerAdapter(getAwards(), context)
        //mRecyclerView.adapter = mAdapter
    }

    fun getAwards() {
        var awards:MutableList<Award> = ArrayList()

        val queue = Volley.newRequestQueue(context)
        val url = "https://app-api.assadi.io/api/prices/"

        val jsonRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    println("Response is: $response")
                    for (i in 0..(response.length() - 1)) {
                        val a_id : Int = response.getJSONObject(i).getInt("id")
                        val award : String = response.getJSONObject(i).getString("price")
                        val cost : Int = response.getJSONObject(i).getString("score").toInt()
                        val a_url : String = response.getJSONObject(i).getString("url")
                        awards.add(Award(a_id,award, cost, a_url))
                    }
                    mAdapter.SecondRecyclerAdapter(awards, context)
                    mRecyclerView.adapter = mAdapter
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    println("That didn't work!")
                })
        queue.add(jsonRequest)

        //awards.add(Award("iPod Classic", 59990, "https://www.bytetotal.com/sites/default/files/styles/nota_full/public/ipod-classic-2009.png?itok=YrV-fTFw"))
        //awards.add(Award("Amazon Echo", 29990, "https://images.ctfassets.net/gdch8ejbyxg5/724b0zh2BU64QsAsQmYIOk/156f188bd06afa7ceddd76ebeb9d4ddd/Amazon_Dot_Front.png"))
        //awards.add(Award("Chromecast", 15990, "https://c1-ebgames.eb-cdn.com.au/merchandising/images/packshots/6b8320e826e24ab99ce42127c4065ce0_Large.png"))
        //return awards
    }
}

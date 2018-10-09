package com.sdp.mousetrap


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sdp.mousetrap.DB.Poll
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class PollsFragment : Fragment() {

    lateinit var mRecyclerView : RecyclerView
    val mAdapter : RecyclerAdapter = RecyclerAdapter()
    var delegate: FragmentDelegate? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is FragmentDelegate) {
            delegate = context
        }
    }

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
        getPolls()
        //mAdapter.RecyclerAdapter(getPolls(), delegate as FragmentDelegate)
        //mRecyclerView.adapter = mAdapter
    }

    fun getPolls() {
        var polls:MutableList<Poll> = ArrayList()
        //polls.add(Poll(1,"Burger King", 12000, Date(2018, 5, 12), true, 2, Date(2019, 5, 7), 20, 500, "Are we better that KFC?", "https://www.festisite.com/static/partylogo/img/logos/burger-king.png"))
        val queue = Volley.newRequestQueue(context)
        val url = "https://app-api.assadi.io/api/surveys/"

        val jsonArray = JSONArray()
        val jsonObject = JSONObject()
        jsonObject.put("id",1)
        jsonArray.put(jsonObject)

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,url,jsonArray,
                Response.Listener { response ->
                    for (i in 0..(response.length() - 1)) {
                        val item = response.getJSONObject(i)
                        val id = item.getString("url").removeSurrounding("http://localhost:8000/api/surveys/","/").toInt()
                        val client = item.getString("client")
                        val cost = item.getInt("cost")
                        val creation_date = item.getString("creation_date").substringBefore("T")
                        val creation_date_vals = creation_date.split("-")
                        val creation_date_formated = Date (creation_date_vals[0].toInt(),creation_date_vals[1].toInt(),creation_date_vals[2].toInt())
                        val anonymous = item.getBoolean("anonymous")
                        val score = item.getInt("score")
                        val deadline = item.getString("end_date").substringBefore("T")
                        val deadline_vals = deadline.split("-")
                        val deadline_formated = Date (deadline_vals[0].toInt(),deadline_vals[1].toInt(),deadline_vals[2].toInt())
                        val minimum_answers = item.getInt("minimum_answers")
                        val maximum_answers = item.getInt("maximum_answers")
                        val description = item.getString("description")
                        val image_url = item.getString("logo")

                        println("Response is: $item")
                        polls.add(Poll(id,client, cost, creation_date_formated, anonymous, score, deadline_formated, minimum_answers, maximum_answers, description, image_url))
                    }
                    mAdapter.RecyclerAdapter(polls, delegate as FragmentDelegate)
                    mRecyclerView.adapter = mAdapter
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    println("Network error!")
                })

        queue.add(jsonArrayRequest)
        /*polls.add(Poll("Nike", 25000, Date(2018, 8, 12), true, 2, Date(2019, 5, 12), 20, 200, "Do you JUST DO IT?", "https://us1.hatstoremedia.com/hatstore/images/image-nike-kepsar-2017-02-21-114202041/555/555/0/nike-kepsar.png"))
        polls.add(Poll("Microsoft", 6000, Date(2018, 7, 12), true, 5, Date(2019, 5, 13), 100, 190, "Tell us what's wrong with Edge.", "https://www.pointofsuccess.com/wp-content/uploads/Microsoft-Logo.png"))
        polls.add(Poll("Santa Isabel", 7800, Date(2018, 5, 12), true, 2, Date(2019, 5, 5), 80, 250, "Who was Saint Isabel?", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/49/Logo_Santa_Isabel_Cencosud_transparente.svg/1024px-Logo_Santa_Isabel_Cencosud_transparente.svg.png"))
        polls.add(Poll("AWS", 18900, Date(2018, 12, 12), true, 2, Date(2019, 9, 12), 20, 200, "Let's talk about prime.", "http://www.crein.com/blog/wp-content/uploads/2013/05/amazon-ec2.png"))
        polls.add(Poll("Paris", 15500, Date(2018, 1, 12), true, 3, Date(2019, 2, 12), 45, 200, "Customer satisfaction", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Logo_Paris_Cencosud.png/230px-Logo_Paris_Cencosud.png"))
        polls.add(Poll("Universidad de los Andes", 10000, Date(2018, 5, 12), true, 2, Date(2019, 5, 12), 20, 200, "Writing center sucks", "https://s3-us-west-2.amazonaws.com/enterreno-production/users/avatars/000/001/575/thumb/Logo_uandes.png"))
        polls.add(Poll("Reebook", 17450, Date(2018, 4, 12), true, 10, Date(2019, 4, 12), 75, 200, "Better thar PUMA, right?", "https://www.seeklogo.net/wp-content/uploads/2014/06/reebok-vector-logo.png"))*/
    }
}

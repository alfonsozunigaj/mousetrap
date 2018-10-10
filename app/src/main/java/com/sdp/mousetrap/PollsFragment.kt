package com.sdp.mousetrap


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

    private val Preferences_name : String = "Prefs"
    private var Preferences: SharedPreferences? = null

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
        Preferences = this.activity.getSharedPreferences(Preferences_name, Context.MODE_PRIVATE)
        val user_id : Int = Preferences!!.getInt("id", 0)
        val queue = Volley.newRequestQueue(context)
        val url = "https://app-api.assadi.io/api/surveys/?u_id=$user_id"
        println("Response is: $url")

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,url,null,
                Response.Listener { response ->
                    for (i in 0..(response.length() - 1)) {
                        val item = response.getJSONObject(i)
                        val id = item.getString("id").toInt()
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
  }
}

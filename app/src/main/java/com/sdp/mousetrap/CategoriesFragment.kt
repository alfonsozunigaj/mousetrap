package com.sdp.mousetrap


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sdp.mousetrap.DB.Category


class CategoriesFragment : Fragment() {

    lateinit var mRecyclerView : RecyclerView
    val mAdapter : RecyclerAdapterCategories = RecyclerAdapterCategories()
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
        var view: View = inflater.inflate(R.layout.fragment_categories, container, false)
        setUpRecyclerView(view)
        return view
    }


    fun setUpRecyclerView(view: View){
        mRecyclerView = view.findViewById(R.id.rvCategoriesList) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        getCategories(view)
    }


    fun setUpNew(view: View, categories: ArrayList<Category>){
        val new: TextView = view.findViewById(R.id.add_new) as TextView
        new.setOnClickListener {
            var transaction = delegate!!.createFragmentManager().beginTransaction()
            delegate!!.createFragmentManager().popBackStack()
            transaction.replace(R.id.main_frame, NewCategoryFragment.newInstance(categories))
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


    fun getCategories(view: View) {

        Preferences = this.activity.getSharedPreferences(Preferences_name, Context.MODE_PRIVATE)
        val user_id : Int = Preferences!!.getInt("id", 0)

        val queue = Volley.newRequestQueue(context)
        val url = "https://app-api.assadi.io/api/user_categories/?u_id=$user_id"

        val jsonRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    println("Response is: $response")
                    val category_ids = ArrayList<Int>()
                    for (i in 0..(response.length() - 1)) {
                        val id = response.getJSONObject(i).getString("category").toInt()
                        category_ids.add(id)
                    }
                    getCategoriesNames(view, category_ids)
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    println("That didn't work!")
                })
        queue.add(jsonRequest)
    }

    fun getCategoriesNames(view: View, category_ids: ArrayList<Int>){
        var categories: ArrayList<Category> = ArrayList()
        val queue = Volley.newRequestQueue(context)
        val url = "https://app-api.assadi.io/api/category_values/"

        val jsonRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    println("Response is: $response")
                    val category_names = ArrayList<String>()
                    for (i in 0..(response.length() - 1)) {
                        val id = response.getJSONObject(i).getInt("id")
                        if (category_ids.contains(id)){
                            category_names.add(response.getJSONObject(i).getString("value"))
                        }
                    }
                    for (i in 0..(category_names.size - 1)) {
                        categories.add(Category(i, category_names.get(i)))
                    }
                    setUpNew(view, categories)
                    mAdapter.RecyclerAdapterCategories(categories, delegate as FragmentDelegate)
                    mRecyclerView.adapter = mAdapter

                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    println("That didn't work!")
                })
        queue.add(jsonRequest)
    }


}

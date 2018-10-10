package com.sdp.mousetrap


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sdp.mousetrap.DB.Category


class CategoriesFragment : Fragment() {

    lateinit var mRecyclerView : RecyclerView
    val mAdapter : RecyclerAdapterCategories = RecyclerAdapterCategories()
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
        var view: View = inflater.inflate(R.layout.fragment_categories, container, false)
        setUpRecyclerView(view)
        return view
    }


    fun setUpRecyclerView(view: View){
        mRecyclerView = view.findViewById(R.id.rvCategoriesList) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        var categories: ArrayList<Category> = getCategories()
        setUpNew(view, categories)
        mAdapter.RecyclerAdapterCategories(categories, delegate as FragmentDelegate)
        mRecyclerView.adapter = mAdapter
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


    fun getCategories(): ArrayList<Category> {
        var categories: ArrayList<Category> = ArrayList()
        categories.add(Category(1, "Ingeniero"))
        categories.add(Category(2, "Gamer"))
        categories.add(Category(3, "Jardineria"))
        categories.add(Category(4, "Cocina"))
        categories.add(Category(5, "Catolicismo"))
        categories.add(Category(6, "Deportes Extremos"))
        categories.add(Category(7, "Playa"))
        categories.add(Category(8, "Viajar"))
        categories.add(Category(9, "Perros"))
        categories.add(Category(10, "Historia"))
        return categories
    }


}

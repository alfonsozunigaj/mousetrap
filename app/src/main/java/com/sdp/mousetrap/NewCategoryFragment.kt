package com.sdp.mousetrap


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.sdp.mousetrap.DB.Category
import java.io.Serializable


class NewCategoryFragment : Fragment() {
    companion object {
        fun newInstance(categories: ArrayList<Category>): NewCategoryFragment {
            val fragment = NewCategoryFragment()
            val args = Bundle()
            args.putSerializable("categories", categories)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_new_category, container, false)
        val categories: ArrayList<Category> = arguments["categories"] as ArrayList<Category>
        setUpSpinner(view, categories)
        return view
    }


    fun setUpSpinner(view: View, categories: ArrayList<Category>) {
        val spinner: Spinner = view.findViewById(R.id.spinner) as Spinner
        var items: ArrayList<String> = ArrayList()
        for (i in 0 until categories.count()){
            items.add((categories[i]).name)
        }
        var adapter: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }


}

package com.sdp.mousetrap


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class UserFragment : Fragment() {

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
        var view: View = inflater.inflate(R.layout.fragment_user, container, false)
        setUpUser(view)
        setUpCategories(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    fun setUpUser(view: View) {
        val cheese_score = view.findViewById(R.id.cheese_score) as TextView
        cheese_score.text = "3260 " + getEmojiByUnicode(0x1F9C0)
    }

    fun setUpCategories(view: View) {
        val categories = view.findViewById(R.id.categories) as ImageView
        categories.setOnClickListener {
            var transaction = delegate!!.createFragmentManager().beginTransaction()
            transaction.replace(R.id.main_frame, CategoriesFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

}
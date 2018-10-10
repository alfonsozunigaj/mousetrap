package com.sdp.mousetrap


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class UserFragment : Fragment() {

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
        var view: View = inflater.inflate(R.layout.fragment_user, container, false)
        setUpUser(view)
        setUpCategories(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    fun setUpUser(view: View) {
        Preferences = this.activity.getSharedPreferences(Preferences_name, Context.MODE_PRIVATE)
        val user_id : Int = Preferences!!.getInt("id", 0)

        val queue = Volley.newRequestQueue(context)
        val url = "https://app-api.assadi.io/api/users/$user_id"

        val jsonRequest = JsonObjectRequest(url, null,
                Response.Listener { response ->
                    println("Response is: $response")
                    val cheeses = response.getInt("cheeses")
                    val cheese_score = view.findViewById(R.id.cheese_score) as TextView
                    cheese_score.text = "$cheeses " + getEmojiByUnicode(0x1F9C0)
                    val cheese_score2 = view.findViewById(R.id.tv1) as TextView
                    cheese_score2.text = "$cheeses"

                    val username = response.getString("username")
                    val usernametv = view.findViewById(R.id.name) as TextView
                    usernametv.text = "$username"

                    val email = response.getString("email")
                    val emailtv = view.findViewById(R.id.user_email) as TextView
                    emailtv.text = "($email)"

                    val rut = response.getString("rut")
                    val ruttv = view.findViewById(R.id.rut) as TextView
                    ruttv.text = "$rut"

                    val gender = response.getString("gender")
                    val gendertv = view.findViewById(R.id.gender) as TextView
                    gendertv.text = "$gender"

                    val birth = response.getString("birth_date")
                    val birthtv = view.findViewById(R.id.bd) as TextView
                    birthtv.text = "$birth"
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    println("That didn't work!")
                })
        queue.add(jsonRequest)
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
package com.sdp.mousetrap

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sdp.mousetrap.DB.Award
import com.squareup.picasso.Picasso
import org.json.JSONObject

class SecondViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.findViewById(R.id.tittle) as TextView
    val avatar = view.findViewById(R.id.logo) as ImageView

    private val Preferences_name : String = "Prefs"
    private var Preferences: SharedPreferences? = null

    @SuppressLint("SetTextI18n")
    fun bind(award: Award, context: Context){
        title.text = award.name
        title.text = award.price.toString() + ' ' + getEmojiByUnicode(0x1F9C0)
        avatar.loadUrl(award.image_url)
        itemView.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmation Message")
            builder.setMessage("Are you sure you want to cash in this reward?")
            builder.setPositiveButton("YES") {dialog, which ->
                Preferences = context.getSharedPreferences(Preferences_name, Context.MODE_PRIVATE)
                val user_id : Int = Preferences!!.getInt("id", 0)

                val queue = Volley.newRequestQueue(context)
                val url = "https://app-api.assadi.io/api/users/$user_id"

                val jsonRequest = JsonObjectRequest(url, null,
                        Response.Listener { response ->
                            println("Response is: $response")
                            val cheeses : Int = response.getInt("cheeses")
                            if (cheeses - award.price >= 0){
                                //Toast.makeText(context, "Ha Stranger!!", Toast.LENGTH_SHORT).show()
                                buyAward(user_id, award, context)
                            }
                            else{
                                Toast.makeText(context, "Not enough cash!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        Response.ErrorListener { error ->
                            error.printStackTrace()
                            println("That didn't work!")
                        })
                queue.add(jsonRequest)
            }
            builder.setNegativeButton("NO") {dialog, which ->
                Toast.makeText(context, "Operation cancelled", Toast.LENGTH_SHORT).show()
            }
            var dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
    fun ImageView.loadUrl(url: String) {
        Picasso.with(context).load(url).into(this)
    }

    fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

    fun buyAward(user_id: Int, award: Award, context: Context){
        val queue = Volley.newRequestQueue(context)
        val url = "https://app-api.assadi.io/api/user_prices/"
        val jsonObject = JSONObject()
        jsonObject.put("price",award.id)
        jsonObject.put("date","2018-02-02T01:01:00Z")
        jsonObject.put("user",user_id)

        println("json: $jsonObject")

        val jsonRequest = JsonObjectRequest(url, jsonObject,
                Response.Listener { response ->
                    println("Response is: $response")
                    Toast.makeText(context, "Cashed in " + award.name, Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    println("That didn't work!")
                })
        queue.add(jsonRequest)
    }
}
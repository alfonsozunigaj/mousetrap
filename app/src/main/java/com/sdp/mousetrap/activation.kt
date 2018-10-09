package com.sdp.mousetrap

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class activation : AppCompatActivity() {
    private lateinit var email : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activation)
        email = findViewById(R.id.email) as EditText
        val send_btn = findViewById(R.id.send_btn) as Button
        send_btn.setOnClickListener{
            activation_request()
        }
        val back_btn = findViewById(R.id.back_btn) as Button
        back_btn.setOnClickListener{
            finish()
        }
    }

    fun activation_request(){
        val e = email.text.toString().trim(' ')
        val queue = Volley.newRequestQueue(this)
        val url = "https://app-api.assadi.io/api/activate/$e" //hay que cambiarlo por link de activacion.
        println("Response is: $e")

        val jsonRequest = JsonObjectRequest(url, null,
                Response.Listener { response ->
                    println("Response is: $response")
                    finish()
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    Toast.makeText(this, "Network Problem.", Toast.LENGTH_SHORT).show()
                    println("That didn't work!")
                })
        queue.add(jsonRequest)
    }
}

package com.sdp.mousetrap

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Login : AppCompatActivity() {
    private lateinit var user : EditText
    private lateinit var password : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        user = findViewById(R.id.email) as EditText
        password = findViewById(R.id.password) as EditText
        val btn = findViewById(R.id.login_btn) as Button
        btn.setOnClickListener{
            login_request()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }

    fun login_request(){

        val u = user.text.toString()
        val p = password.text.toString()
        //val u = "admin"
        //val p = "password123"
        val queue = Volley.newRequestQueue(this)
        val url = "https://app-api.assadi.io/api/login/"

        val jsonObject = JSONObject()
        jsonObject.put("username",u)
        jsonObject.put("password",p)

        val stringRequest = JsonObjectRequest(url, jsonObject,
                Response.Listener { response ->
                    println("Response is: $response")
                    val token = response.getString("token")
                    val id = response.getInt("user_id")
                    val email = response.getString("email")
                    val username = response.getString("username")
                    val data = Intent()
                    data.putExtra("token", token)
                    data.putExtra("id", id)
                    data.putExtra("email", email)
                    data.putExtra("username", username)
                    setResult(RESULT_OK, data)
                    finish()
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    Toast.makeText(this, "Wrong Username/Password.", Toast.LENGTH_SHORT).show()
                    println("That didn't work!")
                })

        queue.add(stringRequest)

    }
}

package com.sdp.mousetrap

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val user = findViewById(R.id.email) as EditText
        val btn = findViewById(R.id.login_btn) as Button
        btn.setOnClickListener{
            val email = user.text.toString()
            val data = Intent()
            data.putExtra("token", email)
            setResult(RESULT_OK, data)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }
}

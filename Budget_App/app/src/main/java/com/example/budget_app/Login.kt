package com.example.budget_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //go to sign up page
        val button: Button = findViewById(R.id.btnSignup)
        button.setOnClickListener {
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }

        //go to gestion page
        val button2: Button = findViewById(R.id.log)
        button2.setOnClickListener {
            val intent = Intent(this@Login, Gestion::class.java)
            startActivity(intent)
        }

    }
}
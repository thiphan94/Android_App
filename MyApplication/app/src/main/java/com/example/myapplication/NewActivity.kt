package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        title = "Budget App"
        val button: Button = findViewById(R.id.button2)
        button.setOnClickListener {
            val i = Intent(this@NewActivity, MainActivity::class.java)
            startActivity(i)
        }
    }
}
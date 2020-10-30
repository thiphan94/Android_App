package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Budget App"
        val button:Button = findViewById(R.id.button1)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, Gestion::class.java)
            startActivity(intent)
        }
    }
}
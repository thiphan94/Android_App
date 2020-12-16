package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Money Balance"

        //*******go to Activity Login
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
        }

        //*******Animation for image of pig
        val animation1 = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val icon: ImageView = findViewById(R.id.imageView)
        icon.animation = animation1;

        val animation2 = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        val button2: Button = findViewById(R.id.button)
        button2.animation = animation2

    }
}
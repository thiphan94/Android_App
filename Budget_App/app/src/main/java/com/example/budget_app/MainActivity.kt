package com.example.budget_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Budget App"
        val button:Button = findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
        }


        val animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val icon: ImageView = findViewById(R.id.imageView)
        icon.animation = animation;

        val animation_button = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        val button2: Button = findViewById(R.id.button)
        button2.animation = animation_button;
    }
}


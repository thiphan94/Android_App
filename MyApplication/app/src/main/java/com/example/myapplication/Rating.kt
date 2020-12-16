package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class Rating : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {
    private lateinit var display: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        //******RatingBar
        val rating: RatingBar = findViewById(R.id.ratingBar)
        display = findViewById(R.id.rating_display)
        rating.onRatingBarChangeListener = this
    }
    //*******RatingBar
    override fun onRatingChanged(ratingBar: RatingBar?, p1: Float, p2: Boolean) {
        display.text = "$p1"
        val db = FirebaseFirestore.getInstance()
        val rating = display.text.toString()
        db.collection("rating").add(RatingItem(rating)).addOnCompleteListener {
        }
    }
}
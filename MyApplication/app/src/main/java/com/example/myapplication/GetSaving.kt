package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*

class GetSaving : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_saving)


        val db = FirebaseFirestore.getInstance()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        db.collection("saving").orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                var list = ArrayList<SavingItem>()
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        list.add(
                            SavingItem(
                                document.get("date") as String,
                                document.get("amount") as Double
                            )
                        )
                    }
                    var adapter =SavingAdapter(list)
                    recyclerView.adapter = adapter
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }




    }
}
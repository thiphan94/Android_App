package com.example.myapplication

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class GetData : AppCompatActivity() {
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_data)



        db = FirebaseFirestore.getInstance()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        db.collection("data")
            .get()
            .addOnCompleteListener { task ->
                var list = ArrayList<DataItem>()
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        list.add(
                            DataItem(
                                document.get("types") as String,
                                document.get("date") as String,
                                document.get("amount") as Double
                            )
                        )
                    }
                    var adapter = DataAdapter(list)
                    recyclerView.adapter = adapter
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }

    }




}



package com.example.myapplication

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

//for display data (income and expense avec date, amount and type)
class GetData : AppCompatActivity() {
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_data)



        db = FirebaseFirestore.getInstance()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        db.collection("data").orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                var list = ArrayList<DataItem>()
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        list.add(
                            DataItem(
                                document.get("type") as String,
                                document.get("date") as String,
                                document.get("amount") as Double,
                                document.get("category") as String
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



package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*

//for display savings (with date, amount) to RecyclerView
class GetSaving : AppCompatActivity() {
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_saving)
        val button = findViewById<Button>(R.id.button_get)

        //*******Chose date

        val mPickTimeBtn = findViewById<Button>(R.id.pickDateBtn)
        val textView     = findViewById<TextView>(R.id.dateTv)
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        mPickTimeBtn.setOnClickListener {

        val dpd = DatePickerDialog(
                this,
                { _, selyear, monthOfYear, dayOfMonth ->
                    day = dayOfMonth
                    month = monthOfYear + 1
                    year = selyear
                    //check day and month, if they are small then 10, add 0 before day and month
                    //for the format yyyy/mm/dd
                    if ((day in 1..9) && (month in 1..9)) {
                        textView.text = "$year/0$month/0$day"
                    }
                    else{
                        if (day in 1..9) {
                            textView.text = "$year/$month/0$day"
                        }
                        else{
                            if (month in 1..9) {
                                textView.text = "$year/0$month/$day"
                            }
                            else {
                                textView.text = "$year/$month/$day"

                            }
                        }
                    }
                    val date = textView.text.toString()

                    db = FirebaseFirestore.getInstance()
                    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    val query = db.collection("saving")
                    query
                            .whereEqualTo("date", date)
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
                }, year, month, day
        )

        dpd.show()

        }

        button.setOnClickListener {
            db = FirebaseFirestore.getInstance()
            val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(this)
            val query = db.collection("saving")
            query
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

}
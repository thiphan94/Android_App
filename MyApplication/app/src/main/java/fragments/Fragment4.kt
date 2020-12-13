package fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class Fragment4 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_4, container, false)
        val button: Button = v.findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this@Fragment4.context, GetData::class.java)
            startActivity(intent)
        }
        //val recyclerView = v.findViewById<RecyclerView>(R.id.recycler_view)
        //val db = FirebaseFirestore.getInstance()
        /*
        val query  = db.collection("data")

        var fire : FirestoreRecyclerOptions<DataItem> = FirestoreRecyclerOptions.Builder<DataItem>()
            .setQuery(query, DataItem::class.java)
            .build();

        var dataadapter = DataAdapter(fire)

        recyclerView.layoutManager = LinearLayoutManager(v.context)
        recyclerView.adapter = dataadapter

        */
        /*
        db.collection("data")
            .get()
            .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>)
            {
                override fun onComplete(p0: Task<QuerySnapshot>){
                    var list = ArrayList<DataItem>()
                    if (p0.isSuccessful) {
                        for (document in p0.result!!) {
                            list.add(
                                DataItem(
                                    document.get("types") as String,
                                    document.get("date") as String
                                )
                            )
                        }
                        var adapter = DataAdapter(list)
                        recyclerView.adapter = adapter
                    } else {
                        Log.w(TAG, "Error getting documents.", p0.exception)
                    }
            }

            }
        */
        /*
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
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
        */
        return v
    }




}

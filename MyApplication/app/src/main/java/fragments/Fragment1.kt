package fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.firebase.firestore.FirebaseFirestore


class Fragment1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_1, container, false)

        val textDisplay: TextView = v.findViewById(R.id.textView7)

        val db = FirebaseFirestore.getInstance()


        val query1 = db.collection("data")


        query1
            .whereEqualTo("types", "Income")
            .get()
            .addOnSuccessListener { documents ->
                var total = 0.0
                for (document in documents) {
                    val itemCost = document.getDouble("amount")!!
                    total += itemCost
                }
                textDisplay.text = total.toString()
            }

            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        return v
    }
}
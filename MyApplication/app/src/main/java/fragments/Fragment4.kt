package fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions


class Fragment4 : Fragment(), RatingBar.OnRatingBarChangeListener {
    private lateinit var display: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_4, container, false)
        val data: TextView = v.findViewById(R.id.data)

        //when click Data, go to Activity GetData
        data.setOnClickListener {
            val intent = Intent(this@Fragment4.context, GetData::class.java)
            startActivity(intent)
        }

        //when click Saving, go to Activity GetSaving
        val saving: TextView = v.findViewById(R.id.saving)
        saving.setOnClickListener {
            val intent = Intent(this@Fragment4.context, GetSaving::class.java)
            startActivity(intent)
        }


        //******RatingBar
        val rating: RatingBar = v.findViewById(R.id.ratingBar)
        display = v.findViewById(R.id.rating_display)
        rating.onRatingBarChangeListener = this

        //**********Logout
        var auth = FirebaseAuth.getInstance()
        auth.addAuthStateListener {
            if (auth.currentUser == null) {
                activity?.finish()
            }
        }
        val logout: Button = v.findViewById(R.id.btn_logout)
        logout.setOnClickListener {
            Toast.makeText(v.context, "Logging Out...", Toast.LENGTH_LONG).show()
            auth.signOut()
        }


        //*******Spinner of currency
        val types = arrayOf("EUR", "USD", "CNY", "SGD", "JYP", "CAD", "RUB")
        val spinner = v.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_item, types)
            spinner.adapter = arrayAdapter
        }

        val db = FirebaseFirestore.getInstance()
        val set: Button = v.findViewById(R.id.btn_set)
        set.setOnClickListener {
            val type = spinner.selectedItem.toString() // selected value of spinner  >>> string

            db.collection("currency").document("item").set(CurrencyItem(type))

        }


        return v
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

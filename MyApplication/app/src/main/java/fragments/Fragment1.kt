package fragments

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.firebase.firestore.FirebaseFirestore
import java.nio.file.Paths.get
import java.util.*


class Fragment1 : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_1, container, false)

        val textDisplay: TextView = v.findViewById(R.id.textView7)
        val currency: TextView = v.findViewById(R.id.currency)
        val currency2: TextView = v.findViewById(R.id.currency2)
        val currency3: TextView = v.findViewById(R.id.currency3)
        val expense: TextView = v.findViewById(R.id.number_display)
        val saving: TextView = v.findViewById(R.id.number2_display)
        val date_display: TextView = v.findViewById(R.id.text_date)

        val db = FirebaseFirestore.getInstance()

        //**********Query total income and display to a TextView : textDisplay
        val query1 = db.collection("data")

        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)

        //fix error of month
        month = if (month == 12){
            1
        } else{
            month + 1
        }
        //fix month_end
        var month2: Int = month
        month2 = if (month == 12){
            1
        } else{
            month2 + 1
        }
        //fix year_end
        var year2 = year
        if(month2 == 1){
            year2 += 1
        }

        var start: String
        var end: String
        var display = ""

        //format of current month
        if (month in 1..9) {
            start = "$year/0$month/01"
            end = "$year2/0$month2/01"
            display = "0$month/$year"
        }
        else{
            if (month == 12){
                start = "$year/$month/01"
                end = "$year2/0$month2/01"
                display = "0$month/$year"
            }
            else{
                start = "$year/$month/01"
                end = "$year2/$month2/01"
            }
        }

        // display current month to text
        date_display.text = display


        //query of total income in month current
        query1
            .whereEqualTo("type", "Income")
            .whereGreaterThanOrEqualTo("date", start)
            .whereLessThan("date", end)
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





        //Query display currency and display at a TextView: currency

        val query4 = db.collection("currency").document("item")

        query4
                .get()
                .addOnSuccessListener { document ->
                    if(document.exists()){
                        currency.text = document.getString("type")
                        currency2.text = document.getString("type")
                        currency3.text = document.getString("type")
                    }
                }

                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }

        //Query display total expense and display at a TextView: number_display

        val query2 = db.collection("data")

        query2
            .whereEqualTo("type", "Expense")
            .whereGreaterThanOrEqualTo("date", start)
            .whereLessThan("date", end)
            .get()
            .addOnSuccessListener { documents ->
                var total = 0.0
                for (document in documents) {
                    val itemCost = document.getDouble("amount")!!
                    total += itemCost
                }
                expense.text = total.toString()
            }

            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        //Query display total saving and display at a TextView: number2_display

        val query3 = db.collection("saving")

        query3
            .whereGreaterThanOrEqualTo("date", start)
            .whereLessThan("date", end)
            .get()
            .addOnSuccessListener { documents ->
                var total = 0.0
                for (document in documents) {
                    val itemCost = document.getDouble("amount")!!
                    total += itemCost
                }
                saving.text = total.toString()
            }

            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        return v
    }
}
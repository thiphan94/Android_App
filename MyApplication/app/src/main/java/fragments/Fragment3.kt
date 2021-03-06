package fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.myapplication.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Handler
import android.os.Message
import android.text.TextUtils

import android.widget.SeekBar
import com.example.myapplication.DataItem
import com.example.myapplication.SavingItem
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class Fragment3 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_3, container, false)

        //*******Chose date

        val mPickTimeBtn = v.findViewById<Button>(R.id.pickDateBtn)
        val textView     = v.findViewById<TextView>(R.id.dateTv)

        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)
        mPickTimeBtn.setOnClickListener {

            val dpd = DatePickerDialog(
                v.context,
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
                }, year, month, day
            )

            dpd.show()
        }

        //******* Write data to Firestore

        val db = FirebaseFirestore.getInstance()
        val button = v.findViewById<Button>(R.id.button)
        val money    = v.findViewById<TextView>(R.id.amount)
        
        button.setOnClickListener {
            val date = textView.text.toString() //date

            var amount : Double = 0.0

            //check if you enter date or amount or not
            amount = if(TextUtils.isEmpty(money.text.toString())) {
                0.0
            } else {
                money.text.toString().toDouble() //amount
            }
            if(date.trim().isEmpty() || amount == 0.0 ){
                Toast.makeText(
                        v.context, "Please enter date or amount  !",
                        Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            db.collection("saving").add(SavingItem(date,amount)).addOnCompleteListener {
                Toast.makeText(v.context, "Saved ! ", Toast.LENGTH_SHORT).show()
            }
        }
        return v
    }

}
package fragments

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.myapplication.CustomDropDownAdapter
import com.example.myapplication.DataItem
import com.example.myapplication.Model
import com.example.myapplication.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import java.util.*


class Fragment2 : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_2, container, false)

        //*******Spinner image + text for les categories
        val spinner = v.findViewById<Spinner>(R.id.spinner)

        //function read data from file json to List<Model> which contain image and icon of categories
        fun readFromAsset(): List<Model> {
            //file in folder assets
            val file_name = "android_version.json"//file contain name and icon of categories

            val bufferReader = v.context.assets.open(file_name).bufferedReader()

            val json_string = bufferReader.use {
                it.readText()
            }
            val gson = Gson()

            val modelList: List<Model> =
                gson.fromJson(json_string, Array<Model>::class.java).toList()
            return modelList
        }
        val modelList: List<Model> = readFromAsset()

        val customDropDownAdapter = CustomDropDownAdapter(v.context, modelList)
        spinner.adapter = customDropDownAdapter


        //*******Spinner for types ( Income or Expense)

        val types = arrayOf("Income", "Expense")
        val spinner2 = v.findViewById<Spinner>(R.id.spinner2)
        if (spinner2 != null) {
            val arrayAdapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_item, types)
            spinner2.adapter = arrayAdapter

        }

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
                    if (day in 1..9) {
                        textView.text = "$year/0$month/0$day"
                    } else {
                        textView.text = "$year/$month/$day"
                    }

                }, year, month, day
            )

            dpd.show()
        }
        //*******Write data to Firestore

        val db = FirebaseFirestore.getInstance()
        val button = v.findViewById<Button>(R.id.button)
        val money    = v.findViewById<TextView>(R.id.amount)

        var idx: String ="" //id of selected item of spinner categories

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                idx = modelList[position].getId()// get id of selected item of spinner categories
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }

        button.setOnClickListener {
            val type = spinner2.selectedItem.toString() // selected value of spinner (Income or Expense) >>> string
            val date = textView.text.toString() //date
            val category = idx // selected value of spinner (categories) >>> string
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
            else{
                db.collection("data").add(DataItem(type, date, amount, category)).addOnCompleteListener {
                    Toast.makeText(v.context, "Saved ! ", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return v
    }

}


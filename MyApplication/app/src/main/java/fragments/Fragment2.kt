package fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

        //Spinner image + text for les categories
        val spinner = v.findViewById<Spinner>(R.id.spinner)

        /*

        if (spinner != null) {


            //Categories of expense
            val categories = arrayOf(
                "Transportation",
                "Food",
                "Healthcare",
                "Education",
                "Entertainment",
                "Love",
                "Groceries",
                "Make up",
                "Travel"
            )


            val image = intArrayOf(
                R.drawable.ecocar,
                R.drawable.lunchbag,
                R.drawable.healthy,
                R.drawable.school,
                R.drawable.tickets,
                R.drawable.love,
                R.drawable.groceries,
                R.drawable.makeup,
                R.drawable.travel
            )





            val spinnerCustomAdapter = SpinnerCustomAdapter(v.context, image, categories);
            spinner.adapter=spinnerCustomAdapter


        }


         */

        fun readFromAsset(): List<Model> {
            val file_name = "android_version.json"

            val bufferReader = v.context.assets.open( file_name ).bufferedReader()

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





        //******************Spinner for type ( Income or Expense)

        val type = arrayOf("Income", "Expense")
        val spinner2 = v.findViewById<Spinner>(R.id.spinner2)
        if (spinner2 != null) {
            val arrayAdapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_item, type)
            spinner2.adapter = arrayAdapter

        }


        //********************Chose date

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
                    if (day == 1)
                    {
                        textView.text = "$year/$month/0$day"
                    }
                    else{
                        textView.text = "$year/$month/$day"
                    }


                }, year, month, day
            )


            dpd.show()
        }
        //********************


        val db = FirebaseFirestore.getInstance()
        val data = HashMap<String, Any>()
        val button = v.findViewById<Button>(R.id.button)
        val money    = v.findViewById<TextView>(R.id.amount)


        button.setOnClickListener {
            val types = spinner2.selectedItem.toString() // selected value of spinner (Income or Expense) >>> string
            val date = textView.text.toString() //date
            val amount = money.text.toString().toDouble() //amount
            //val category2 = spinner.selectedItem.toString()// selected value of spinner (categories) >>> string

            db.collection("data").add(DataItem(types, date, amount)).addOnCompleteListener {
                Toast.makeText(v.context, "Saved ! ", Toast.LENGTH_SHORT).show()
            }

        }


        return v
    }
    /*
    class SpinnerCustomAdapter(
        internal var context: Context,
        internal var flags: IntArray,
        internal var Network: Array<String>
    ) : BaseAdapter() { internal var inflter: LayoutInflater
        init {
            inflter = LayoutInflater.from(context)
        }
        override fun getCount(): Int {
            return flags.size
        }
        override fun getItem(i: Int): Any? {
            return null
        }
        override fun getItemId(i: Int): Long {
            return 0
        }
        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            var view = view
            view = inflter.inflate(R.layout.custom_spinner_items, null)
            val icon = view.findViewById(R.id.spinner_imageView) as ImageView
            val names = view.findViewById(R.id.spinner_textView) as TextView
            icon.setImageResource(flags[i])
            names.text = Network[i]
            return view
        }
    }*/

}


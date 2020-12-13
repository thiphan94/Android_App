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
import com.example.myapplication.DataItem
import com.example.myapplication.R
import com.google.firebase.firestore.FirebaseFirestore
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



        //Spinner for type ( Income or Expense)

        val type = arrayOf("Income", "Expense")
        val spinner2 = v.findViewById<Spinner>(R.id.spinner2)
        if (spinner2 != null) {
            val arrayAdapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_item, type)
            spinner2.adapter = arrayAdapter

        }


        //Chose date
        /*
        val mPickTimeBtn = v.findViewById<Button>(R.id.pickDateBtn)
        val textView     = v.findViewById<TextView>(R.id.dateTv)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mPickTimeBtn.setOnClickListener {

            val dpd = DatePickerDialog(
                v.context,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    textView.setText("" + dayOfMonth + " " + month + ", " + year)
                },
                year,
                month,
                day
            )
            dpd.show()

        }*/

        val mPickTimeBtn = v.findViewById<Button>(R.id.pickDateBtn)
        val textView     = v.findViewById<TextView>(R.id.dateTv)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mPickTimeBtn.setOnClickListener {

            val dpd = DatePickerDialog(v.context, { _, year, _, dayOfMonth ->
                // Display Selected date in TextView
                textView.text = "$dayOfMonth $month, $year"
            }, year, month, day)
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
    }

}


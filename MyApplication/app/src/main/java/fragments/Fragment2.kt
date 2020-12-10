package fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.myapplication.*
import java.text.SimpleDateFormat
import java.util.*


class Fragment2 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_2, container, false)

        //Spinner image + text for les categories
        val spinner = v.findViewById<Spinner>(R.id.spinner)

        if (spinner != null) {
            //Categories of expenses
            val categories = arrayOf("Transportation", "Food", "Healthcare", "Education", "Entertainment", "Love", "Groceries", "Make up", "Travel")
            val image = intArrayOf(R.drawable.ecocar, R.drawable.lunchbag, R.drawable.healthy, R.drawable.school, R.drawable.tickets, R.drawable.love, R.drawable.groceries, R.drawable.makeup,  R.drawable.travel )
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

        val mPickTimeBtn = v.findViewById<Button>(R.id.pickDateBtn)
        val textView     = v.findViewById<TextView>(R.id.dateTv)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mPickTimeBtn.setOnClickListener {

            val dpd = DatePickerDialog(v.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                textView.setText("" + dayOfMonth + " " + month + ", " + year)
            }, year, month, day)
            dpd.show()

        }

        //********************


        val db = DataBaseHandler(v.context)
        val button = v.findViewById<Button>(R.id.button)
        val textView2     = v.findViewById<TextView>(R.id.dateTv)
        val textView3     = v.findViewById<TextView>(R.id.editTextNumberDecimal2)

        button.setOnClickListener {
            if(textView2.text.toString().isNotEmpty() && textView3.text.toString().isNotEmpty()){
                val user = User(textView2.text.toString(),textView3.text.toString().toInt())
                db.insertData(user)
            }else{
                Toast.makeText(activity,"Please Fill All Data's",Toast.LENGTH_LONG).show()
            }
        }
        return v
    }

    class SpinnerCustomAdapter(internal var context: Context, internal var flags: IntArray, internal var Network: Array<String>) : BaseAdapter() { internal var inflter: LayoutInflater
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
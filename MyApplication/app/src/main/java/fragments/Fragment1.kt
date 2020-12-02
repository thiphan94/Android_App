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
import com.example.myapplication.Expense
import com.example.myapplication.First
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.*


class Fragment1 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_1, container, false)

        val newPage =
                v.findViewById<View>(R.id.button) as Button
        newPage.setOnClickListener {
            val intent = Intent(activity, Expense::class.java)
            startActivity(intent)
        }

        //Return Home page
        val button:Button = v.findViewById(R.id.button2)
        button.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        //Categories of expenses
        val categories = arrayOf("Cinema", "Comestique", "Cours", "School", "Hobbit", "Love")
        val spinner = v.findViewById<Spinner>(R.id.spinner)

        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_item, categories)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(activity, getString(R.string.selected_item) + " " + categories[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        // Create an ArrayAdapter
        val adapter = ArrayAdapter.createFromResource(v.context,
                R.array.city_list, android.R.layout.simple_spinner_item)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner.adapter = adapter

        //***************

        val type = arrayOf("Income", "Expense")
        val spinner2 = v.findViewById<Spinner>(R.id.spinner2)
        if (spinner2 != null) {
            val arrayAdapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_item, type)
            spinner2.adapter = arrayAdapter

            spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(activity, getString(R.string.selected_item) + " " + type[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        //*******************

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
        return v
    }

}
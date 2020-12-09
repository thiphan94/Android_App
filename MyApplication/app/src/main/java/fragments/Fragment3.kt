package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.myapplication.R


class Fragment3 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_3, container, false)


        //Categories of expenses
        //val categories = arrayOf("Cinema", "Comestique", "Cours", "School", "Hobbit", "Love")
        val categories = resources.getStringArray(R.array.categories)
        val spinner = v.findViewById<Spinner>(R.id.spinner)

        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(v.context, R.layout.spinner_layout, R.id.text, categories)
            spinner.adapter = arrayAdapter

        }
        return v
    }
}
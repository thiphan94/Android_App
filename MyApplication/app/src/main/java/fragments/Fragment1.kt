package fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.myapplication.Expense
import com.example.myapplication.R


class Fragment1 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_1, container, false)

        val newPage =
                v.findViewById<View>(R.id.button) as Button
        newPage.setOnClickListener {
            val intent = Intent(activity, Expense::class.java)
            startActivity(intent)
        }
        return v
    }
}
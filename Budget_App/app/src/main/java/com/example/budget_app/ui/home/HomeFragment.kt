package com.example.budget_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.budget_app.Expense
import com.example.budget_app.R


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_home, container, false)

        val newPage =
            v.findViewById<View>(R.id.button4) as Button
        newPage.setOnClickListener {
            val intent = Intent(activity, Expense::class.java)
            startActivity(intent)
        }
        return v
    }
}

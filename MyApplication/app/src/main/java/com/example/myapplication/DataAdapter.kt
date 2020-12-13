package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.doOnAttach
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.text.FieldPosition

class DataAdapter(var list: ArrayList<DataItem>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var type  = itemView.findViewById(R.id.type) as TextView
        var date = itemView.findViewById(R.id.date) as TextView
        var amount = itemView.findViewById(R.id.amount) as TextView
        var category = itemView.findViewById(R.id.category) as TextView
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.type.text = list[position].type
        holder.date.text = list[position].date
        holder.amount.text = list[position].amount.toString()
        holder.category.text = list[position].category

    }





}
package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.DataBaseHandler
import com.example.myapplication.R
import com.example.myapplication.User


class Fragment4 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_4, container, false)
        val context = this
        val db = DataBaseHandler(v.context)
        val button = v.findViewById<Button>(R.id.insertButton)
        val button2 = v.findViewById<Button>(R.id.readButton)
        val button3 = v.findViewById<Button>(R.id.updateButton)
        val button4 = v.findViewById<Button>(R.id.deleteButton)
        val textView     = v.findViewById<TextView>(R.id.resultText)
        val textView2     = v.findViewById<TextView>(R.id.nameText)
        val textView3     = v.findViewById<TextView>(R.id.ageText)
        button.setOnClickListener {
            if(textView2.text.toString().isNotEmpty() && textView3.text.toString().isNotEmpty()){
                val user = User(textView2.text.toString(),textView3.text.toString().toInt())
                db.insertData(user)
            }else{
                Toast.makeText(activity,"Please Fill All Data's",Toast.LENGTH_LONG).show()
            }
        }
        button2.setOnClickListener {
            val data : MutableList<User> = db.readData()
            if(data.isNotEmpty())
                textView.append("Read\n")
            for(i in 0 until data.size){
                textView.append("ID : ${data[i].id} NAME : ${data[i].name} AGE : ${data[i].age}\n")
            }
        }
        button3.setOnClickListener{
            db.updateData()
            val data : MutableList<User> = db.readData()
            if(data.isNotEmpty())
                textView.append("Read\n")
            for(i in 0 until data.size){
                textView.append("ID : ${data[i].id} NAME : ${data[i].name} AGE : ${data[i].age}\n")
            }
        }
        button4.setOnClickListener{
            db.deleteData()
            textView.text=""
        }


        return v
    }

}

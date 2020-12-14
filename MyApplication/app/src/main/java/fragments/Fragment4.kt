package fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class Fragment4 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_4, container, false)
        val button: Button = v.findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this@Fragment4.context, GetData::class.java)
            startActivity(intent)
        }

        //**********Logout
        var auth = FirebaseAuth.getInstance()
        auth.addAuthStateListener {
            if (auth.currentUser == null){
                activity?.finish()
            }
        }
        val logout: Button = v.findViewById(R.id.btn_logout)
        logout.setOnClickListener {
            Toast.makeText(v.context, "Logging Out...", Toast.LENGTH_LONG).show()
            auth.signOut()

        }




        return v
    }




}

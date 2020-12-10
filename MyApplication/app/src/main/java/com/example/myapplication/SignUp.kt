package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //return Login page
        val button: Button = findViewById(R.id.login)
        button.setOnClickListener {
            val intent = Intent(this@SignUp, Login::class.java)
            startActivity(intent)
        }

        val button2: Button = findViewById(R.id.button3)
        button2.setOnClickListener {
            performRegister()
        }



    }

    private fun performRegister(){
        val email : EditText = findViewById(R.id.mail)
        val password : EditText = findViewById(R.id.password)

        val string1 = email.text.toString()
        val string2 = password.text.toString()

        if(string1.isEmpty() ||string2.isEmpty()){
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("SignUp","Email is " + email)
        Log.d("SignUp","Password :  $password")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(string1, string2)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("SignUp", "Successfully created user : ${it.result?.user?.uid}")
                Toast.makeText(this, "Successfully created user ${it.result?.user?.uid}", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener{
                Log.d("SignUp", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }

    }
}
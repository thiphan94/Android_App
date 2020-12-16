package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

//Log in to account
class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //*******go to Activity SignUp
        val button: Button = findViewById(R.id.btnSignup)
        button.setOnClickListener {
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }

        //*******go to Activity ResetPasswordActivity
        val reset: TextView = findViewById(R.id.reset)
        reset.setOnClickListener {
            val intent = Intent(this@Login, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        //*******Login
        //check if firebase user is present or not
        //If present, go to Activity First
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            val intent = Intent(this, First::class.java)
            startActivity(intent)
            this.finish()
        }


        val button2: Button = findViewById(R.id.log)
        button2.setOnClickListener {
            val email : EditText = findViewById(R.id.mail)
            val password : EditText = findViewById(R.id.password)

            val string1 = email.text.toString()
            val string2 = password.text.toString()

            Log.d("Login", "Attempt login with email/pw: $email/***")

            //check if EditText of email and password are empty or not
            if(string1.isEmpty() && string2.isEmpty()){
                Toast.makeText(
                    this, "Please enter your email and password !",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            else {

                //log in
                FirebaseAuth.getInstance().signInWithEmailAndPassword(string1, string2)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success
                            Log.d("Login", "signInWithEmail:success")
                            val intent = Intent(this@Login, First::class.java)
                            startActivity(intent)

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

    }
}
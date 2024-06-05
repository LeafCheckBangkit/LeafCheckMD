package com.example.leafcheck.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.leafcheck.MainActivity
import com.example.leafcheck.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val registerLink = findViewById<TextView>(R.id.btn_register)
        registerLink.setOnClickListener {
            val intent = Intent(
                this@LoginActivity,
                RegistrasiActivity::class.java
            )
            startActivity(intent)
        }

        val buttonSignUp = findViewById<TextView>(R.id.btn_action)
        buttonSignUp.setOnClickListener {
            val intent = Intent(
                this@LoginActivity,
                MainActivity::class.java
            )
            startActivity(intent)
        }
    }
}
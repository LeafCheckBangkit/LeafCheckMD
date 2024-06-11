package com.example.leafcheck.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.leafcheck.R

class RegistrasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val registerLink = findViewById<TextView>(R.id.btn_login)
        registerLink.setOnClickListener {
            val intent = Intent(
                this@RegistrasiActivity,
                LoginActivity::class.java
            )
            startActivity(intent)
        }

    }

}
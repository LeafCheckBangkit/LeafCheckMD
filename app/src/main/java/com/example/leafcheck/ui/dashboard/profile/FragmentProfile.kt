package com.example.leafcheck.ui.dashboard.profile

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.leafcheck.R
import com.example.leafcheck.ui.auth.LoginActivity


class FragmentProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        val buttonLogOut = findViewById<TextView>(R.id.btn_logout)
        buttonLogOut.setOnClickListener {
            val intent = Intent(
                this@FragmentProfile,
                LoginActivity::class.java
            )
            startActivity(intent)
        }
    }
}
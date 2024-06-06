package com.example.leafcheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.leafcheck.ui.dashboard.home.FragmentHome
import com.example.leafcheck.ui.dashboard.profile.FragmentProfile

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_profile -> {
                startActivity(Intent(this, FragmentProfile::class.java))
                true
            }
            R.id.navigation_home -> {
                startActivity(Intent(this, FragmentHome::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
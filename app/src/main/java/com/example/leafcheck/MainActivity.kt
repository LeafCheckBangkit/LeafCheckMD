package com.example.leafcheck

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.leafcheck.ui.dashboard.home.CameraActivity
import com.example.leafcheck.ui.dashboard.home.FragmentHome
import com.example.leafcheck.ui.dashboard.profile.FragmentProfile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_profile -> {
                    loadFragment(FragmentProfile())
                    true
                }
                R.id.navigation_home -> {
                    loadFragment(FragmentHome())
                    true
                }
                else -> false
            }
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }

        // Load the default fragment
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.navigation_home
        }
    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_profile -> {
                loadFragment(FragmentProfile())
                true
            }
            R.id.navigation_home -> {
                loadFragment(FragmentHome())
                true
            }
            R.id.fab -> {
                startActivity(Intent(this, CameraActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

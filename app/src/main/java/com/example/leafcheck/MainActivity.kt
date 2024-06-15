package com.example.leafcheck

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.leafcheck.databinding.ActivityMainBinding
import com.example.leafcheck.databinding.ActivityScanBinding
import com.example.leafcheck.ui.view.CameraActivity
import com.example.leafcheck.ui.dashboard.home.FragmentHome
import com.example.leafcheck.ui.dashboard.profile.FragmentProfile
import com.example.leafcheck.ui.view.ResultActivity
import com.example.leafcheck.ui.view.ScanActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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

//        binding.fab.setOnClickListener {
//            navigateToScan()
//        }


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }

        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.navigation_home
        }
    }
//    private fun navigateToScan() {
//        val intent = Intent(this, ScanActivity::class.java)
//        startActivity(intent)
//    }

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
                startActivity(Intent(this, ScanActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

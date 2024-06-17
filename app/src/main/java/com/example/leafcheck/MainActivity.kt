package com.example.leafcheck

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.leafcheck.databinding.ActivityMainBinding
import com.example.leafcheck.ui.dashboard.home.FragmentHome
import com.example.leafcheck.ui.dashboard.profile.FragmentProfile
import com.example.leafcheck.ui.view.ScanActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        setupFab()
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.navigation_home
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
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
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            navigateToScan()
        }
    }

    private fun navigateToScan() {
        val intent = Intent(this, ScanActivity::class.java)
        startActivity(intent)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .commit()
    }
}

package com.example.leafcheck.ui.dashboard.home

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.leafcheck.R

class CameraActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val buttonBack = findViewById<ImageView>(R.id.btn_back)
        buttonBack.setOnClickListener {
            val navController = Navigation.findNavController(this, R.id.container)
            navController.navigate(R.id.navigation_home)
        }
    }
}

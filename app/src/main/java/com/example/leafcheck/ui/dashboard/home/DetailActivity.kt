package com.example.leafcheck.ui.dashboard.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.leafcheck.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get data from Intent
        val dataGame = intent.getParcelableExtra<News>("key_news")

        // Display data on the detail layout
        val tvDetailName = findViewById<TextView>(R.id.tv_detail_name)
        val tvDetailDescription = findViewById<TextView>(R.id.tv_detail_description)
        val ivDetailPhoto = findViewById<ImageView>(R.id.iv_detail_photo)

        // Ensure dataGame is not null before displaying
        dataGame?.let {
            tvDetailName.text = it.name
            tvDetailDescription.text = it.description
            ivDetailPhoto.setImageResource(it.photo)
        }
    }
}

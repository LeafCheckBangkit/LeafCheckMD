package com.example.leafcheck.ui.dashboard.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leafcheck.R

class FragmentHome : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListNewsAdapter
    private val newsList = arrayListOf<News>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViews)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Initialize Adapter
        adapter = ListNewsAdapter(newsList)
        recyclerView.adapter = adapter

        // Load News Data
        loadNewsData()

        return view
    }

    private fun loadNewsData() {
        // Example data, replace this with actual data
        newsList.add(News("News Title 1", "News Description 1", R.drawable.ic_launcher_background))
        newsList.add(News("News Title 2", "News Description 2", R.drawable.ic_launcher_background))
        // Notify adapter about data changes
        adapter.notifyDataSetChanged()
    }
}

package com.example.leafcheck.ui.dashboard.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        // Load data
        newsList.addAll(getListHeroes())
        adapter.notifyDataSetChanged()

        // Set item click callback
        adapter.setOnItemClickCallback(object : ListNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: News) {
                showSelectedHero(data)
            }
        })

        return view
    }

    private fun getListHeroes(): ArrayList<News> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listHero = ArrayList<News>()
        for (i in dataName.indices) {
            val hero = News(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listHero.add(hero)
        }
        dataPhoto.recycle()
        return listHero
    }

    private fun showSelectedHero(hero: News) {
        Toast.makeText(activity, "You selected " + hero.name, Toast.LENGTH_SHORT).show()
    }
}

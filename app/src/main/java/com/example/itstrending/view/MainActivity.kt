package com.example.itstrending.view

import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itstrending.R
import com.example.itstrending.viewmodel.TrendingViewModel
import com.example.itstrending.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: TrendingViewModel
    lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview = findViewById(R.id.recyclerView)
        viewModel = ViewModelProvider(this, ViewModelFactory(this.application))
            .get(TrendingViewModel::class.java)
        setUpRecycler()
    }

    private fun setUpRecycler() {
        //defining layout manager
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        //init recyclerview with layout manager
        recyclerview.apply {
            recyclerview.layoutManager = layoutManager
        }
        setAndObserveAdapter()
    }

    private fun setAndObserveAdapter() {
        viewModel.fetchTrendingReposResponse().observe(this, {
            recyclerview.adapter = TrendingReposAdapter(viewModel, it.items, this)
        })
    }
}
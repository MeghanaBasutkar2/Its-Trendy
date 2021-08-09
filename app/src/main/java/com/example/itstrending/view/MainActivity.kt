package com.example.itstrending.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itstrending.viewmodel.TrendingViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: TrendingViewModel
    lateinit var recyclerview: RecyclerView
    lateinit var mainAdapter: TrendingReposAdapter
    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.itstrending.R.layout.activity_main)
        recyclerview = findViewById(com.example.itstrending.R.id.recyclerView)
        progress = findViewById(com.example.itstrending.R.id.progressBar)
        viewModel = ViewModelProvider(this).get(TrendingViewModel::class.java)
        setUpRecycler()
    }

    private fun setUpRecycler() {
        //defining layout manager
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mainAdapter = TrendingReposAdapter(viewModel, this)
        recyclerview.apply {
            this.layoutManager = layoutManager
            this.adapter = mainAdapter
        }
        progress.visibility = View.VISIBLE
        observeChangesInList()
    }

    private fun observeChangesInList() {
        viewModel.fetchTrendingReposResponse().observe(this, { mainAdapter.setList(it)
        progress.visibility = View.GONE})
    }
}
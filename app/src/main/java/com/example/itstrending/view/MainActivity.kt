package com.example.itstrending.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.itstrending.R
import com.example.itstrending.viewmodel.TrendingViewModel
import androidx.recyclerview.widget.DividerItemDecoration




class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: TrendingViewModel
    private lateinit var recyclerview: RecyclerView
    private lateinit var mainAdapter: TrendingReposAdapter
    private lateinit var progress: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        recyclerview = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        progress = findViewById(R.id.progressBar)
        viewModel = ViewModelProvider(this).get(TrendingViewModel::class.java)
        swipeRefreshLayout.setOnRefreshListener {
            onSwipeToRefresh()
        }
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.blue))
        setUpRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    private fun onSwipeToRefresh() {
        observeChangesInList()
    }

    private fun setUpRecycler() {
        //defining layout manager
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mainAdapter = TrendingReposAdapter(viewModel, this)

        recyclerview.apply {
            this.layoutManager = layoutManager
            this.adapter = mainAdapter
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        }
        progress.visibility = View.VISIBLE
        observeChangesInList()
    }

    private fun observeChangesInList() {
        viewModel.fetchTrendingReposResponse().observe(this, {
            mainAdapter.setList(it)
            progress.visibility = View.GONE
            //stop refreshing once user swipes & data is fetched from API call
            swipeRefreshLayout.apply {
                if (isRefreshing) isRefreshing = false
            }
        }
        )
    }
}
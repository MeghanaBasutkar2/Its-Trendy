package com.example.itstrending.view

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.itstrending.R
import com.example.itstrending.viewmodel.TrendingViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.itstrending.data.TrendingResponse


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: TrendingViewModel
    private lateinit var recyclerview: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var listRepos: ArrayList<TrendingResponse.ItemsObj>

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
        var searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        var menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem?.actionView as SearchView
        searchView.apply {
            queryHint = resources.getString(R.string.txt_search)
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        setupSearchQuery(searchView)
        return true

    }

    private fun setupSearchQuery(searchView: SearchView) {
        val queryChangedListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(entry: String): Boolean {
                    listRepos.filter {
                        it.name?.contains(entry)!! || it.description.contains(entry)
                        return true
                    }
                    return true
                }

                override fun onQueryTextSubmit(p0: String): Boolean {
                    Toast.makeText(applicationContext, "search :$p0", Toast.LENGTH_LONG)
                        .show()
                    return false
                }
            }
        searchView.setOnQueryTextListener(queryChangedListener)
    }

    private fun onSwipeToRefresh() {
        observeList()
    }

    private fun setUpRecycler() {
        //defining layout manager
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        recyclerview.apply {
            this.layoutManager = layoutManager
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        }
        progress.visibility = View.VISIBLE
        observeList()
    }

    private fun observeList() {
        viewModel.fetchTrendingReposResponse().observe(this, {
            if (it != null) {
                recyclerview.adapter = TrendingReposAdapter(viewModel, it.items, this)
                listRepos = it.items
            }
            progress.visibility = View.GONE
            //stop refreshing once user swipes & data is fetched from API call
            swipeRefreshLayout.apply {
                if (isRefreshing) isRefreshing = false
            }
        })
    }
}
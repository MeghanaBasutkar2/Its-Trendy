package com.example.itstrending.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.itstrending.data.TrendingResponse
import com.example.itstrending.repository.NetworkRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendingViewModel(var stateHandle: SavedStateHandle) : ViewModel() {
    var reposList: MutableLiveData<TrendingResponse> = MutableLiveData<TrendingResponse>()
    private var identifier: String = "SAVED_POS"

    fun getSelectedIndex(): LiveData<Int> {
        return stateHandle.getLiveData(identifier)
    }

    fun setSelectedWithIndex(position: Int) {
        stateHandle.set(identifier, position)
    }

    /**
     * fun executes the fetch API call
     * */
    fun fetchTrendingReposResponse(): LiveData<TrendingResponse> {
        val response = NetworkRepository.getTrendingRepos()
        response?.enqueue(object : Callback<TrendingResponse> {
            override fun onResponse(call: Call<TrendingResponse>,
                response: Response<TrendingResponse>) {
                reposList.postValue(response.body()) //updates the live data obj asynchronously
            }

            override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                t.message?.let { Log.e("error-----------> ", it) }
            }
        })
        return reposList
    }
}
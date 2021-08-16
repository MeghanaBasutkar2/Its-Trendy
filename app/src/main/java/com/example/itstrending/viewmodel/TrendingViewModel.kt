package com.example.itstrending.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.itstrending.data.TrendingResponse
import com.example.itstrending.repository.NetworkRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendingViewModel(var stateHandle: SavedStateHandle) : ViewModel() {
    private var identifier: String = "SAVED_POS"

    fun getSelectedIndex(): LiveData<Int> {
        return stateHandle.getLiveData(identifier)
    }

    fun setSelectedWithIndex(position: Int) {
        stateHandle.set(identifier, position)
    }

    var reposList = MutableLiveData<TrendingResponse>().apply {
        viewModelScope.launch { fetchRepos() }
    }

    /**
     * fun executes the fetch API call
     * */
    private fun fetchRepos() {
        val response =
            NetworkRepository.getTrendingRepos()
        response?.enqueue(object : Callback<TrendingResponse> {
            override fun onResponse(
                call: Call<TrendingResponse>,
                response: Response<TrendingResponse>
            ) {
                reposList.postValue(response.body()) //updates the live data obj asynchronously
            }

            override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                t.message?.let { Log.e("error-----------> ", it) }
            }
        })
    }
}
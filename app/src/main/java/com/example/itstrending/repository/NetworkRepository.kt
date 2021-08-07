package com.example.itstrending.repository

import com.example.itstrending.data.TrendingResponse
import com.example.itstrending.network.TrendingReposApiClient
import retrofit2.Call

class NetworkRepository {
    companion object {
        private var baseUrl: String = "https://api.github.com/"

        /**
         * fun fetches trending repos from the API client
         * */
        fun getTrendingRepos(): Call<TrendingResponse>? {
            return TrendingReposApiClient.getInstance(baseUrl).getTrendingRepos()
        }
    }
}
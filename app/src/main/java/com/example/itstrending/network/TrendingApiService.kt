package com.example.itstrending.network

import com.example.itstrending.data.TrendingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApiService {
    @GET("search/repositories")
    fun getTrendingRepos(
        @Query("q") platform: String,
        @Query("per_page") itemsPerPage: Int,
        @Query("page") page: Int,
    )
            : Call<TrendingResponse>
}
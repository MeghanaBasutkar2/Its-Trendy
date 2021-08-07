package com.example.itstrending.network

import com.example.itstrending.data.TrendingResponse
import retrofit2.Call

class TrendingReposApiClient(baseUrl: String) :
    BaseHttpClient<TrendingApiService>(baseUrl) {
    private var page: Int = 1
    private var platform: String = "android"
    private var itemsCount: Int = 20

    companion object {
        private var INSTANCE: TrendingReposApiClient? = null

        /**
         * although we could use object declaration instead of class to achieve singleton,
         * we do not do that here as objects cannot parse data.
         * Thus, we create a fun to get instance of the class
         * */
        fun getInstance(baseUrl: String): TrendingReposApiClient {
            return when {
                INSTANCE != null -> INSTANCE!!
                else -> TrendingReposApiClient(baseUrl)
            }
        }
    }

    /**
     * fun sets the service class for the retrofit builder in the base class
     * */
    override fun getApiServiceClass(): Class<out TrendingApiService> {
        return TrendingApiService::class.java
    }

    /**
     * service fun to fetch trending repos
     */
    fun getTrendingRepos(): Call<TrendingResponse>? {
        return service?.getTrendingRepos(platform, itemsCount, page)
    }
}
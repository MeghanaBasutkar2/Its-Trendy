package com.example.itstrending.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//if there is just one base url then, one could just hardcode it in this class
// instead of constructor parsing
abstract class BaseHttpClient<S>(baseURL: String) {
    private var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit
    var service: S? = null

    /**
     * sets up Http client whenever subclassed by a module specific API client
     * */
    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(getGsonConverter()))
            .client(httpClient.build())
            .build()
        createAPIService()
    }

    /**
     * parse APIServiceClass by forced override in subclass
     * */
    abstract fun getApiServiceClass(): Class<out S>

    /**
     * fun creates retrofit with the provided service class
     * */
    private fun createAPIService() {
        service = retrofit.create(getApiServiceClass())
    }

    private fun getGsonConverter(): Gson {
        return GsonBuilder().create()
    }
}
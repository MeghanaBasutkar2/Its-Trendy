package com.example.itstrending.data

import com.google.gson.annotations.SerializedName

class TrendingResponse {
    lateinit var items: ArrayList<ItemsObj>

    data class ItemsObj(
        var name: String?,
        var owner: Owner, var description: String,
        @SerializedName("forks_count") var forksCount: Double
    )

    class Owner(@SerializedName("avatar_url") val avatar: String)
}
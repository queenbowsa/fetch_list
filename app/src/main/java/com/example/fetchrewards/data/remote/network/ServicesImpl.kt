package com.example.fetchrewards.data.remote.network

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import android.util.Log
import com.example.fetchrewards.data.remote.dto.ItemResponse

class ServicesImpl(
    private val client: HttpClient
) : Services {
    override suspend fun getItems(): List<ItemResponse> {
        return try {
            client.get(HttpRoutes.FETCH_ENDPOINT).body()
        } catch (e: Exception) {
            Log.e("TagName", "${e.message}")
            emptyList() // or throw a custom error if you prefer
        }
    }

}
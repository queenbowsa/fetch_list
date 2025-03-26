package com.example.fetchrewards.data.remote.network

import com.example.fetchrewards.data.remote.dto.ItemResponse

interface Services {
    suspend fun getItems(): List<ItemResponse>
}

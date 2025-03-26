package com.example.fetchrewards.data.remote.network

import com.example.fetchrewards.data.remote.dto.ItemResponse

interface Services {
    suspend fun getItems(): List<ItemResponse>
/*
    companion object {
        fun create(): Services {
            return ServicesImpl(
                client = HttpClient(OkHttp) {
                    install(ContentNegotiation) {
                        json(Json {
                            ignoreUnknownKeys = true
                            prettyPrint = false
                            isLenient = true
                        })
                    }
                }
            )
        }
    }*/
}
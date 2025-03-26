package com.example.fetchrewards.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemResponse(
    val id: Int,
    val listId: Int,
    val name: String?
)

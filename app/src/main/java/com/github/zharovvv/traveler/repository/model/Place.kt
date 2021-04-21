package com.github.zharovvv.traveler.repository.model

import java.io.Serializable

data class Place(
    val id: Long,
    val cityId: Long,
    val name: String,
    val description: String,
    val coordinates: String,
    val imageUrl: String
) : Serializable
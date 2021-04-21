package com.github.zharovvv.traveler.repository.model

import java.io.Serializable

data class City(
    val id: Long,
    val name: String,
    val location: String,
    val description: String,
    val imageUrl: String
) : Serializable
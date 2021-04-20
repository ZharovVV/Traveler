package com.github.zharovvv.traveler.repository.model

import java.io.Serializable

data class Route(
    val id: Long,
    val name: String,
    val description: String,
    val places: List<Place>
) : Serializable
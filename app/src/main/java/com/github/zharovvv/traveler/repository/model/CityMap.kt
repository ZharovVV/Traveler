package com.github.zharovvv.traveler.repository.model

import java.io.Serializable

data class CityMap(
    val city: City,
    val places: List<Place>,
    val routes: List<Route>
) : Serializable
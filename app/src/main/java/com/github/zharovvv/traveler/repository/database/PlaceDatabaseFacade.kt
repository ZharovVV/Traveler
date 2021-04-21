package com.github.zharovvv.traveler.repository.database

import com.github.zharovvv.traveler.repository.model.Place

interface PlaceDatabaseFacade {

    fun getPlaceById(placeId: Long): Place?

    fun getPlacesByCityId(cityId: Long): List<Place>

    fun insertPlace(place: Place)

    fun updatePlace(place: Place)

    fun deletePlace(place: Place)
}
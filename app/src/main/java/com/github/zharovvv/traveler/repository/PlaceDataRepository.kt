package com.github.zharovvv.traveler.repository

import com.github.zharovvv.traveler.repository.model.Place
import io.reactivex.Single

interface PlaceDataRepository {

    fun getPlaceData(placeId: Long): Single<Place>
}
package com.github.zharovvv.traveler.repository

import com.github.zharovvv.traveler.repository.model.Place
import io.reactivex.Observable

interface PlaceDataRepository {

    fun getPlaceData(placeId: Long): Observable<Place>
}
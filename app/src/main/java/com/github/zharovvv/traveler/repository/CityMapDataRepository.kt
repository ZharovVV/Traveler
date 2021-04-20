package com.github.zharovvv.traveler.repository

import com.github.zharovvv.traveler.repository.model.CityMap
import io.reactivex.Observable

interface CityMapDataRepository {

    fun getCityMapData(cityId: Long): Observable<CityMap>
}
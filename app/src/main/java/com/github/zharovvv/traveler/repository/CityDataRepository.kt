package com.github.zharovvv.traveler.repository

import com.github.zharovvv.traveler.repository.model.City
import io.reactivex.Observable

interface CityDataRepository {

    fun getCityData(): Observable<List<City>>
}
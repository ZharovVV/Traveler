package com.github.zharovvv.traveler.repository

import com.github.zharovvv.traveler.repository.database.TravelerDatabase
import com.github.zharovvv.traveler.repository.model.CityMap
import com.github.zharovvv.traveler.repository.network.TravelerApiService
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityMapDataRepositoryImpl
@Inject constructor(
    private val travelerApiService: TravelerApiService,
    private val travelerDatabase: TravelerDatabase
) : CityMapDataRepository {

    override fun getCityMapData(cityId: Long): Observable<CityMap> {
        TODO("Not yet implemented")
    }
}
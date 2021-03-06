package com.github.zharovvv.traveler.repository

import com.github.zharovvv.traveler.repository.database.CityMapDatabaseFacade
import com.github.zharovvv.traveler.repository.model.CityMap
import com.github.zharovvv.traveler.repository.network.TravelerApiService
import io.reactivex.Observable

class CityMapDataRepositoryImpl(
    private val travelerApiService: TravelerApiService,
    private val cityMapDatabaseFacade: CityMapDatabaseFacade
) : CityMapDataRepository {

    override fun getCityMapData(cityId: Long): Observable<CityMap> {
        return travelerApiService.getCityMap(cityId.toString())
            .flatMap { response: CityMap ->
                cityMapDatabaseFacade.deleteCityMap(response)
                cityMapDatabaseFacade.insertCityMap(response)
                Observable.just(response)
            }
            .onErrorResumeNext { throwable: Throwable ->
                Observable.just(cityMapDatabaseFacade.getCityMap(cityId))
            }
    }
}
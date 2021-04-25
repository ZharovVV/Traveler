package com.github.zharovvv.traveler.repository

import com.github.zharovvv.traveler.repository.database.CityDatabaseFacade
import com.github.zharovvv.traveler.repository.model.City
import com.github.zharovvv.traveler.repository.network.TravelerApiService
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CityDataRepositoryImpl
@Inject constructor(
    @field:Named("travelerApiServiceStubImpl")
    private val travelerApiService: TravelerApiService,
    private val cityDatabaseFacade: CityDatabaseFacade
) : CityDataRepository {

    override fun getCityData(lastCityId: Long, limit: Long): Observable<List<City>> {
        return travelerApiService.getCities(lastCityId.toString(), limit.toString())
            .flatMap { response: List<City> ->
                cityDatabaseFacade.deleteCities(response)
                cityDatabaseFacade.insertCities(response)
                Observable.just(response)
            }
            .onErrorResumeNext { throwable: Throwable ->
                val citiesFromDatabase = cityDatabaseFacade.getCities(lastCityId, limit)
                Observable.just(citiesFromDatabase)
            }
    }
}
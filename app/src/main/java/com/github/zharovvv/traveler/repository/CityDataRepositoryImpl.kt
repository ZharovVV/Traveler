package com.github.zharovvv.traveler.repository

import com.github.zharovvv.traveler.repository.database.TravelerDatabase
import com.github.zharovvv.traveler.repository.model.City
import com.github.zharovvv.traveler.repository.network.TravelerApiService
import io.reactivex.Observable
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.Subject
import retrofit2.Response
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityDataRepositoryImpl
@Inject constructor(
    private val travelerApiService: TravelerApiService,
    private val travelerDatabase: TravelerDatabase
) : CityDataRepository {

    override fun getCityData(): Observable<List<City>> {
        return travelerApiService.getCities()
//            .flatMap { response: List<City> ->
//                travelerDatabase.cityDao().delete()
//                travelerDatabase.cityDao().insert()
//                Observable.just(response)
//            }
    }
}
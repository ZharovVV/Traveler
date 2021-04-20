package com.github.zharovvv.traveler.repository

import com.github.zharovvv.traveler.repository.database.TravelerDatabase
import com.github.zharovvv.traveler.repository.model.Place
import com.github.zharovvv.traveler.repository.network.TravelerApiService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceDataRepositoryImpl
@Inject constructor(
    private val travelerApiService: TravelerApiService,
    private val travelerDatabase: TravelerDatabase
) : PlaceDataRepository {

    override fun getPlaceData(placeId: Long): Single<Place> {
        TODO("Not yet implemented")
    }
}
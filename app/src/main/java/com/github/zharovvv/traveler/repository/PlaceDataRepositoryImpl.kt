package com.github.zharovvv.traveler.repository

import com.github.zharovvv.traveler.repository.database.PlaceDatabaseFacade
import com.github.zharovvv.traveler.repository.model.Place
import com.github.zharovvv.traveler.repository.network.TravelerApiService
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PlaceDataRepositoryImpl
@Inject constructor(
    @field:Named("travelerApiServiceStubImpl")
    private val travelerApiService: TravelerApiService,
    private val placeDatabaseFacade: PlaceDatabaseFacade
) : PlaceDataRepository {

    override fun getPlaceData(placeId: Long): Observable<Place> {
        return travelerApiService.getPlace(placeId.toString())
            .flatMap { response: Place ->
                if (placeDatabaseFacade.getPlaceById(placeId) != null) {
                    placeDatabaseFacade.updatePlace(response)
                } else {
                    placeDatabaseFacade.insertPlace(response)
                }
                Observable.just(response)
            }
            .onErrorResumeNext { throwable: Throwable ->
                Observable.just(placeDatabaseFacade.getPlaceById(placeId))
            }
    }
}
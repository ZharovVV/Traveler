package com.github.zharovvv.traveler.repository.database

import com.github.zharovvv.traveler.repository.database.converter.PlaceEntityConverter
import com.github.zharovvv.traveler.repository.model.Place

class PlaceDatabaseFacadeImpl(
    private val travelerDatabase: TravelerDatabase,
    private val placeEntityConverter: PlaceEntityConverter
) : PlaceDatabaseFacade {

    override fun getPlaceById(placeId: Long): Place? {
        return travelerDatabase.placeDao().getById(placeId)?.let { placeEntity ->
            placeEntityConverter.convertToModel(placeEntity)
        }
    }

    override fun getPlacesByCityId(cityId: Long): List<Place> {
        return travelerDatabase.placeDao().getByCityId(cityId)
            .map { placeEntity -> placeEntityConverter.convertToModel(placeEntity) }
    }

    override fun insertPlace(place: Place) {
        travelerDatabase.placeDao().insert(placeEntityConverter.convertToEntity(place))
    }

    override fun updatePlace(place: Place) {
        travelerDatabase.placeDao().update(placeEntityConverter.convertToEntity(place))
    }

    override fun deletePlace(place: Place) {
        travelerDatabase.placeDao().delete(placeEntityConverter.convertToEntity(place))
    }
}
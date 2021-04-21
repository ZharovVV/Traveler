package com.github.zharovvv.traveler.repository.database.converter

import com.github.zharovvv.traveler.repository.database.entity.PlaceEntity
import com.github.zharovvv.traveler.repository.model.Place
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceEntityConverter
@Inject constructor() {

    fun convertToModel(entity: PlaceEntity): Place {
        return Place(
            entity.id,
            entity.cityId,
            entity.name,
            entity.description,
            entity.coordinates,
            entity.imageUrl
        )
    }

    fun convertToEntity(model: Place): PlaceEntity {
        return PlaceEntity(
            model.id,
            model.cityId,
            model.name,
            model.description,
            model.coordinates,
            model.imageUrl
        )
    }

}
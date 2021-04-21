package com.github.zharovvv.traveler.repository.database.converter

import com.github.zharovvv.traveler.repository.database.entity.CityEntity
import com.github.zharovvv.traveler.repository.model.City
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityEntityConverter
@Inject constructor() {

    fun convertToModel(entity: CityEntity): City = City(
        entity.id,
        entity.name,
        entity.location,
        entity.description,
        entity.imageUrl
    )

    fun convertToEntity(model: City): CityEntity = CityEntity(
        model.id,
        model.name,
        model.location,
        model.description,
        model.imageUrl
    )

}
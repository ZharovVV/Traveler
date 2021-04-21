package com.github.zharovvv.traveler.repository.database

import com.github.zharovvv.traveler.repository.database.converter.CityEntityConverter
import com.github.zharovvv.traveler.repository.model.City

class CityDatabaseFacadeImpl(
    private val travelerDatabase: TravelerDatabase,
    private val cityEntityConverter: CityEntityConverter
) : CityDatabaseFacade {

    override fun getCities(lastCityId: Long, limit: Long): List<City> {
        return travelerDatabase.cityDao().get(lastCityId, limit)
            .map { cityEntity -> cityEntityConverter.convertToModel(cityEntity) }
    }

    override fun insertCity(city: City) {
        travelerDatabase.cityDao()
            .insert(cityEntityConverter.convertToEntity(city))
    }

    override fun insertCities(cities: List<City>) {
        travelerDatabase.cityDao()
            .insert(cities.map { city -> cityEntityConverter.convertToEntity(city) })
    }

    override fun deleteCities(cities: List<City>) {
        travelerDatabase.cityDao()
            .delete(cities.map { city -> cityEntityConverter.convertToEntity(city) })
    }
}
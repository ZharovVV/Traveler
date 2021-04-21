package com.github.zharovvv.traveler.repository.database

import com.github.zharovvv.traveler.repository.model.City

interface CityDatabaseFacade {

    fun getCities(lastCityId: Long, limit: Long): List<City>

    fun insertCity(city: City)

    fun insertCities(cities: List<City>)

    fun deleteCities(cities: List<City>)
}
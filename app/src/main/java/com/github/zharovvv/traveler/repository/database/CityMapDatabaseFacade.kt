package com.github.zharovvv.traveler.repository.database

import com.github.zharovvv.traveler.repository.model.CityMap

interface CityMapDatabaseFacade {

    fun getCityMap(cityId: Long): CityMap?

    fun insertCityMap(cityMap: CityMap)

    fun deleteCityMap(cityMap: CityMap)
}
package com.github.zharovvv.traveler.repository.database

import com.github.zharovvv.traveler.repository.database.converter.CityEntityConverter
import com.github.zharovvv.traveler.repository.database.converter.PlaceEntityConverter
import com.github.zharovvv.traveler.repository.database.converter.RouteEntityConverter
import com.github.zharovvv.traveler.repository.database.entity.PlaceEntity
import com.github.zharovvv.traveler.repository.model.City
import com.github.zharovvv.traveler.repository.model.CityMap
import com.github.zharovvv.traveler.repository.model.Place
import com.github.zharovvv.traveler.repository.model.Route
import java.util.concurrent.Callable

class CityMapDatabaseFacadeImpl(
    private val travelerDatabase: TravelerDatabase,
    private val cityEntityConverter: CityEntityConverter,
    private val placeEntityConverter: PlaceEntityConverter,
    private val routeEntityConverter: RouteEntityConverter
) : CityMapDatabaseFacade {

    override fun getCityMap(cityId: Long): CityMap? {
        return travelerDatabase.runInTransaction(Callable {
            val city: City? = travelerDatabase.cityDao().get(cityId)?.let { cityEntity ->
                cityEntityConverter.convertToModel(cityEntity)
            }
            val places: List<Place> = travelerDatabase.placeDao().getByCityId(cityId)
                .map { placeEntity -> placeEntityConverter.convertToModel(placeEntity) }
            val routes: List<Route> = travelerDatabase.routeDao().getByCityId(cityId)
                .map { routeEntity ->
                    routeEntityConverter.convertToModel(
                        routeEntity,
                        travelerDatabase.routePlaceDao().getPlacesByRouteId(routeEntity.id)
                    )
                }
            city?.let { CityMap(it, places, routes) }
        })
    }

    override fun insertCityMap(cityMap: CityMap) {
        travelerDatabase.runInTransaction {
            travelerDatabase.cityDao().insert(cityEntityConverter.convertToEntity(cityMap.city))
            val placesEntitySet: MutableSet<PlaceEntity> = cityMap.places
                .map { place ->
                    placeEntityConverter.convertToEntity(place)
                }.toMutableSet()
            val routeContainers = cityMap.routes
                .map { route -> routeEntityConverter.convertToEntity(route) }
            travelerDatabase.routeDao().insert(routeContainers.map { it.routeEntity })
            travelerDatabase.placeDao().insert(
                placesEntitySet.apply {
                    addAll(
                        routeContainers.flatMap {
                            it.placeEntities
                        }
                    )
                }.toList()
            )
            travelerDatabase.routePlaceDao().insert(
                routeContainers.flatMap { it.routePlaceEntities }
            )
        }
    }

    override fun deleteCityMap(cityMap: CityMap) {
        travelerDatabase.cityDao().delete(
            cityEntityConverter.convertToEntity(cityMap.city)
        )
    }
}
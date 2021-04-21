package com.github.zharovvv.traveler.repository.database.converter

import com.github.zharovvv.traveler.repository.database.entity.PlaceEntity
import com.github.zharovvv.traveler.repository.database.entity.RouteEntity
import com.github.zharovvv.traveler.repository.database.entity.RoutePlaceEntity
import com.github.zharovvv.traveler.repository.model.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouteEntityConverter
@Inject constructor(
    private val placeEntityConverter: PlaceEntityConverter
) {

    fun convertToModel(routeEntity: RouteEntity, placeEntities: List<PlaceEntity>): Route {
        return Route(
            routeEntity.id,
            routeEntity.cityId,
            routeEntity.name,
            routeEntity.description,
            placeEntities
                .map {
                    placeEntityConverter.convertToModel(it)
                }
        )
    }

    fun convertToEntity(model: Route): RouteEntityContainer {
        val routeEntity = RouteEntity(
            model.id,
            model.cityId,
            model.name,
            model.description
        )
        val placeEntities = model.places
            .map { place -> placeEntityConverter.convertToEntity(place) }
        val routePlaceEntities = model.places
            .map { place -> RoutePlaceEntity(routeId = model.id, placeId = place.id) }
        return RouteEntityContainer(routeEntity, placeEntities, routePlaceEntities)
    }

    data class RouteEntityContainer(
        val routeEntity: RouteEntity,
        val placeEntities: List<PlaceEntity>,
        val routePlaceEntities: List<RoutePlaceEntity>
    )
}
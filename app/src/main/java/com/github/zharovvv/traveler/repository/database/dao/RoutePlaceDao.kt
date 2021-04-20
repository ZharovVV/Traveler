package com.github.zharovvv.traveler.repository.database.dao

import androidx.room.*
import com.github.zharovvv.traveler.repository.database.entity.PlaceEntity
import com.github.zharovvv.traveler.repository.database.entity.RoutePlaceEntity

@Dao
interface RoutePlaceDao {

    @Query("SELECT p.id, p.city_id, p.name, p.description, p.coordinates, p.image_url FROM place_table AS p INNER JOIN route_place_table as rp ON p.id = rp.place_id WHERE rp.route_id = :routeId")
    fun getPlacesByRouteId(routeId: Long): List<PlaceEntity>

    @Query("SELECT * FROM route_place_table WHERE route_id = :routeId AND place_id = :placeId")
    fun getRoutePlaceEntityByIds(routeId: Long, placeId: Long): RoutePlaceEntity

    @Insert
    fun insert(routePlaceEntity: RoutePlaceEntity): Long

    @Insert
    fun insert(routePlaceEntities: List<RoutePlaceEntity>): List<Long>

    @Update
    fun update(routePlaceEntity: RoutePlaceEntity)

    @Update
    fun update(routePlaceEntities: List<RoutePlaceEntity>)

    @Delete
    fun delete(routePlaceEntity: RoutePlaceEntity)

}
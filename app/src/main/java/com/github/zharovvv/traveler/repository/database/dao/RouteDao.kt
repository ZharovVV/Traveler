package com.github.zharovvv.traveler.repository.database.dao

import androidx.room.*
import com.github.zharovvv.traveler.repository.database.entity.RouteEntity

@Dao
interface RouteDao {

    @Query("SELECT * FROM route_table WHERE city_id=:cityId")
    fun getByCityId(cityId: Long): List<RouteEntity>

    @Insert
    fun insert(routeEntity: RouteEntity): Long

    @Insert
    fun insert(routeEntities: List<RouteEntity>): List<Long>

    @Update
    fun update(routeEntity: RouteEntity)

    @Update
    fun update(routeEntities: List<RouteEntity>)

    @Delete
    fun delete(routeEntity: RouteEntity)
}
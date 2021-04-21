package com.github.zharovvv.traveler.repository.database.dao

import androidx.room.*
import com.github.zharovvv.traveler.repository.database.entity.PlaceEntity

@Dao
interface PlaceDao {

    @Query("SELECT * FROM place_table WHERE city_id=:cityId")
    fun getByCityId(cityId: Long): List<PlaceEntity>

    @Query("SELECT * FROM place_table WHERE id=:placeId")
    fun getById(placeId: Long): PlaceEntity?

    @Insert
    fun insert(placeEntity: PlaceEntity): Long

    @Insert
    fun insert(placeEntities: List<PlaceEntity>): List<Long>

    @Update
    fun update(placeEntity: PlaceEntity)

    @Update
    fun update(placeEntities: List<PlaceEntity>)

    @Delete
    fun delete(placeEntity: PlaceEntity)
}
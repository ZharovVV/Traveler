package com.github.zharovvv.traveler.repository.database.dao

import androidx.room.*
import com.github.zharovvv.traveler.repository.database.entity.CityEntity

@Dao
interface CityDao {

    @Query("SELECT * FROM city_table")
    fun get(): List<CityEntity>

    @Insert
    fun insert(cityEntity: CityEntity): Long

    @Insert
    fun insert(cityEntities: List<CityEntity>): List<Long>

    @Update
    fun update(cityEntity: CityEntity)

    @Update
    fun update(cityEntities: List<CityEntity>)

    @Delete
    fun delete(cityEntity: CityEntity)

    @Delete
    fun delete(cityEntities: List<CityEntity>)

}
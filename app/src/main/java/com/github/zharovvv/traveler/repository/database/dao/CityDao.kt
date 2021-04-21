package com.github.zharovvv.traveler.repository.database.dao

import androidx.room.*
import com.github.zharovvv.traveler.repository.database.entity.CityEntity

@Dao
interface CityDao {

    @Deprecated("Сильно загружает БД")
    @Query("SELECT * FROM city_table")
    fun get(): List<CityEntity>

    @Query("SELECT * FROM city_table WHERE id > :lastCityId ORDER BY id LIMIT :limit")
    fun get(lastCityId: Long, limit: Long): List<CityEntity>

    @Query("SELECT * FROM city_table WHERE id=:cityId")
    fun get(cityId: Long): CityEntity?

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
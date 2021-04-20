package com.github.zharovvv.traveler.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.zharovvv.traveler.repository.database.dao.CityDao
import com.github.zharovvv.traveler.repository.database.dao.PlaceDao
import com.github.zharovvv.traveler.repository.database.dao.RouteDao
import com.github.zharovvv.traveler.repository.database.dao.RoutePlaceDao
import com.github.zharovvv.traveler.repository.database.entity.CityEntity
import com.github.zharovvv.traveler.repository.database.entity.PlaceEntity
import com.github.zharovvv.traveler.repository.database.entity.RouteEntity
import com.github.zharovvv.traveler.repository.database.entity.RoutePlaceEntity

@Database(
    entities = [
        CityEntity::class,
        PlaceEntity::class,
        RouteEntity::class,
        RoutePlaceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TravelerDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun placeDao(): PlaceDao

    abstract fun routeDao(): RouteDao

    abstract fun routePlaceDao(): RoutePlaceDao

}
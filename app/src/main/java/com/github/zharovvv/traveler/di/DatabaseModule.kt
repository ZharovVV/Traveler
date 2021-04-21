package com.github.zharovvv.traveler.di

import androidx.room.Room
import com.github.zharovvv.traveler.TravelerApp
import com.github.zharovvv.traveler.repository.database.*
import com.github.zharovvv.traveler.repository.database.converter.CityEntityConverter
import com.github.zharovvv.traveler.repository.database.converter.PlaceEntityConverter
import com.github.zharovvv.traveler.repository.database.converter.RouteEntityConverter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideTravelerDatabase(): TravelerDatabase {
        return Room.databaseBuilder(
            TravelerApp.appContext,
            TravelerDatabase::class.java,
            "traveler_database"
        )
            .fallbackToDestructiveMigration()   //TODO <-- delete this before release version
            .build()
    }

    @Provides
    @Singleton
    fun provideCityMapDatabaseFacade(
        travelerDatabase: TravelerDatabase,
        cityEntityConverter: CityEntityConverter,
        placeEntityConverter: PlaceEntityConverter,
        routeEntityConverter: RouteEntityConverter
    ): CityMapDatabaseFacade = CityMapDatabaseFacadeImpl(
        travelerDatabase,
        cityEntityConverter,
        placeEntityConverter,
        routeEntityConverter
    )

    @Provides
    @Singleton
    fun provideCityDatabaseFacade(
        travelerDatabase: TravelerDatabase,
        cityEntityConverter: CityEntityConverter
    ): CityDatabaseFacade = CityDatabaseFacadeImpl(travelerDatabase, cityEntityConverter)

    @Provides
    @Singleton
    fun providePlaceDatabaseFacade(
        travelerDatabase: TravelerDatabase,
        placeEntityConverter: PlaceEntityConverter
    ): PlaceDatabaseFacade = PlaceDatabaseFacadeImpl(travelerDatabase, placeEntityConverter)
}
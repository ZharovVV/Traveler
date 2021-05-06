package com.github.zharovvv.traveler.di

import com.github.zharovvv.traveler.repository.*
import com.github.zharovvv.traveler.repository.database.CityDatabaseFacade
import com.github.zharovvv.traveler.repository.database.CityMapDatabaseFacade
import com.github.zharovvv.traveler.repository.database.PlaceDatabaseFacade
import com.github.zharovvv.traveler.repository.network.TravelerApiService
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCityDataRepository(
        @Named("travelerApiServiceStubImpl")
        travelerApiService: TravelerApiService,
        cityDatabaseFacade: CityDatabaseFacade
    ): CityDataRepository {
        return CityDataRepositoryImpl(travelerApiService, cityDatabaseFacade)
    }

    @Provides
    @Singleton
    fun provideCityMapDataRepository(
        @Named("travelerApiServiceStubImpl")
        travelerApiService: TravelerApiService,
        cityMapDatabaseFacade: CityMapDatabaseFacade
    ): CityMapDataRepository {
        return CityMapDataRepositoryImpl(travelerApiService, cityMapDatabaseFacade)
    }

    @Provides
    @Singleton
    fun providePlaceDataRepository(
        @Named("travelerApiServiceStubImpl")
        travelerApiService: TravelerApiService,
        placeDatabaseFacade: PlaceDatabaseFacade
    ): PlaceDataRepository {
        return PlaceDataRepositoryImpl(travelerApiService, placeDatabaseFacade)
    }

}
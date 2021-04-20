package com.github.zharovvv.traveler.di

import androidx.room.Room
import com.github.zharovvv.traveler.TravelerApp
import com.github.zharovvv.traveler.repository.database.TravelerDatabase
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
}
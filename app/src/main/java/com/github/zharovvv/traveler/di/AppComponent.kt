package com.github.zharovvv.traveler.di

import com.github.zharovvv.traveler.ui.screen.CitiesPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, DatabaseModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {
    fun injectCitiesPresenter(citiesPresenter: CitiesPresenter)
}
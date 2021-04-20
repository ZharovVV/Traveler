package com.github.zharovvv.traveler.di

import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, DatabaseModule::class])
@Singleton
interface AppComponent {

}
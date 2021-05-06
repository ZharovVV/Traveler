package com.github.zharovvv.traveler.ui.screen

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.github.zharovvv.traveler.TravelerApp
import com.github.zharovvv.traveler.repository.CityDataRepository
import com.github.zharovvv.traveler.ui.BasePresenter
import javax.inject.Inject

@InjectViewState
class CitiesPresenter : BasePresenter<CitiesMvpView>() {

    init {
        TravelerApp.getAppComponent().injectCitiesPresenter(this)
        Log.i("dagger", "inject $cityDataRepository")  //TODO delete me
    }

    @Inject
    internal lateinit var cityDataRepository: CityDataRepository


}
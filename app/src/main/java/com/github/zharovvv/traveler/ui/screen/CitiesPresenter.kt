package com.github.zharovvv.traveler.ui.screen

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.github.zharovvv.traveler.TravelerApp
import com.github.zharovvv.traveler.repository.CityDataRepository
import com.github.zharovvv.traveler.repository.model.City
import com.github.zharovvv.traveler.ui.BasePresenter
import com.github.zharovvv.traveler.ui.model.Widget
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class CitiesPresenter : BasePresenter<CitiesMvpView>() {

    init {
        TravelerApp.getAppComponent().injectCitiesPresenter(this)
        Log.i("dagger", "inject $cityDataRepository")  //TODO delete me
    }

    @Inject
    internal lateinit var cityDataRepository: CityDataRepository

    private val currentCityWidgetList: MutableList<Widget> = mutableListOf()
    private val onDataLoadingCompletedConsumer: (List<City>) -> Unit =
        { cityList ->
            val newCityWidgets: List<Widget> = cityList
                .map { city ->
                    Widget(
                        city.id.toString(),
                        city.name,
                        city.description,
                        city.imageUrl
                    )
                }
            currentCityWidgetList.addAll(newCityWidgets)
            viewState.updateCities(currentCityWidgetList.toList())
        }

    fun initLoad() {
        cityDataRepository.getCityData(0L, 20L)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .filter { cityList -> cityList.isNotEmpty() }
            .subscribe(
                onDataLoadingCompletedConsumer,
                { error: Throwable ->

                }
            ).keep()
    }

    fun loadNewCities(lastCityWidget: Widget, limit: Int) {
        cityDataRepository.getCityData(lastCityWidget.id.toLong(), limit.toLong())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .filter { cityList -> cityList.isNotEmpty() }
            .subscribe(
                onDataLoadingCompletedConsumer,
                { error: Throwable ->

                }
            ).keep()
    }

    fun onClickCity(widget: Widget) {
        //TODO
    }
}
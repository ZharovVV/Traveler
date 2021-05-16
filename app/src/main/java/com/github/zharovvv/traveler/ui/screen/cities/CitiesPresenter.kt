package com.github.zharovvv.traveler.ui.screen.cities

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
    }

    @Inject
    internal lateinit var cityDataRepository: CityDataRepository

    //Не список из-за кейса: запрос данных -> поворот экрана (после пересоздания запрос повторяется)
    // -> приходит ответ на первый запрос, затем приходит второй запрос с такими же данными
    private val currentCityWidgetSet: MutableSet<Widget> = mutableSetOf()
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
            currentCityWidgetSet.addAll(newCityWidgets)
            if (newCityWidgets.isNotEmpty()) {
                viewState.updateCities(currentCityWidgetSet.toList())
                viewState.hideLoadingIndicator()
            } else {
                viewState.allCitiesLoaded()
            }
        }

    fun initLoad() {
        cityDataRepository.getCityData(0L, 20L)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
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
            .subscribe(
                onDataLoadingCompletedConsumer,
                { error: Throwable ->

                }
            ).keep()
        viewState.showLoadingIndicator()
    }

    fun onClickCity(cityWidget: Widget) {
        viewState.openCity(cityWidget)
    }
}
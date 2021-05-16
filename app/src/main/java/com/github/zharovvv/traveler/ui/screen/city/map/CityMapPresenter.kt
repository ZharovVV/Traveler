package com.github.zharovvv.traveler.ui.screen.city.map

import com.arellomobile.mvp.InjectViewState
import com.github.zharovvv.traveler.ui.BasePresenter
import com.github.zharovvv.traveler.ui.model.Widget

@InjectViewState
class CityMapPresenter : BasePresenter<CityMapMvpView>() {

    fun initLoad(sourceCityWidget: Widget) {
        viewState.bindCityMap(sourceCityWidget)
    }
}
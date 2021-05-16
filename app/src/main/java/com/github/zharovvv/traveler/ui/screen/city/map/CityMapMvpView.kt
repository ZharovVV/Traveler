package com.github.zharovvv.traveler.ui.screen.city.map

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.zharovvv.traveler.ui.model.Widget

@StateStrategyType(AddToEndSingleStrategy::class)
interface CityMapMvpView : MvpView {
    fun bindCityMap(cityMapWidget: Widget)
}
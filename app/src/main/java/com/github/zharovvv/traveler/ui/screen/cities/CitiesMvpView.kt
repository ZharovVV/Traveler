package com.github.zharovvv.traveler.ui.screen.cities

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.zharovvv.traveler.ui.model.Widget

@StateStrategyType(AddToEndSingleStrategy::class)
interface CitiesMvpView : MvpView {

    fun updateCities(cityWidgetList: List<Widget>)

    fun showLoadingIndicator()

    fun hideLoadingIndicator()

    fun allCitiesLoaded()
}
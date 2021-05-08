package com.github.zharovvv.traveler.ui.screen

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.zharovvv.traveler.ui.model.Widget

interface CitiesMvpView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateCities(cityWidgetList: List<Widget>)
}
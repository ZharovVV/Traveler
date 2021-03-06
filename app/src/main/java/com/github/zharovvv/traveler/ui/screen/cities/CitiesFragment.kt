package com.github.zharovvv.traveler.ui.screen.cities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.GridLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.zharovvv.traveler.R
import com.github.zharovvv.traveler.ui.AndroidXMvpAppCompatFragment
import com.github.zharovvv.traveler.ui.adapter.CityListAdapter
import com.github.zharovvv.traveler.ui.model.Widget
import com.github.zharovvv.traveler.ui.screen.city.map.CityMapFragment
import com.github.zharovvv.traveler.ui.view.PaginationRecyclerView

class CitiesFragment : AndroidXMvpAppCompatFragment(),
    CitiesMvpView {

    @InjectPresenter
    internal lateinit var citiesPresenter: CitiesPresenter
    private lateinit var paginationRecyclerView: PaginationRecyclerView<Widget>
    private lateinit var cityListAdapter: CityListAdapter
    private lateinit var rootContainer: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("fragment_lifecycle", "presenter $citiesPresenter")  //TODO delete me
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("fragment_lifecycle", "citiesFragment#onCreateView")
        container?.let { rootContainer = it }
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        Log.i("fragment_lifecycle", "citiesFragment#onViewCreated")
        paginationRecyclerView = view.findViewById(R.id.cities_recycler_view)
        cityListAdapter = CityListAdapter { widget, sharedView -> openCity(widget, sharedView) }
        paginationRecyclerView.apply {
            limit = 10
            layoutManager = GridLayoutManager(
                view.context,
                view.context.resources.getInteger(R.integer.grid_column_count)
            )
            adapter = cityListAdapter
        }
        paginationRecyclerView.addOnNewPageRequiredListener { page ->
            citiesPresenter.loadNewCities(page.lastItem, page.limit)
        }
        if (savedInstanceState == null && !isRestoredFromBackStack) {
            Log.i("Moxy", "citiesPresenter#initLoad")
            citiesPresenter.initLoad()
        }
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun updateCities(cityWidgetList: List<Widget>) {
        val description = if (cityWidgetList.isNotEmpty()) {
            "CitiesMvpView#updateCities; cityWidgetCount: ${cityWidgetList.size}; first city: ${cityWidgetList[0].title}; last city: ${cityWidgetList[cityWidgetList.size - 1].title}"
        } else {
            "CitiesMvpView#updateCities; empty list"
        }
        Log.i("Moxy", description)//TODO delete me
        cityListAdapter.submitCityWidgets(cityWidgetList)
    }

    override fun showLoadingIndicator() {
        Log.i("Moxy", "CitiesMvpView#showLoadingIndicator")
        paginationRecyclerView.onStartPageLoading()
    }

    override fun hideLoadingIndicator() {
        Log.i("Moxy", "CitiesMvpView#hideLoadingIndicator")
        paginationRecyclerView.onNewPageLoaded()
    }

    override fun allCitiesLoaded() {
        Log.i("Moxy", "CitiesMvpView#openCity")
        paginationRecyclerView.onAllPagesLoaded()
    }

    private fun openCity(cityWidget: Widget, sharedElement: View) {
        val cityMapFragment = CityMapFragment()
        cityMapFragment.sourceCityWidget = cityWidget
        prepareTransitionTo(rootContainer, cityMapFragment)
            .setReorderingAllowed(true)
            .addSharedElement(sharedElement, "city_image_view")
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::paginationRecyclerView.isInitialized) {
            paginationRecyclerView.onDestroy()
        }
    }
}
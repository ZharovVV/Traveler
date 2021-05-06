package com.github.zharovvv.traveler.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.zharovvv.traveler.R
import com.github.zharovvv.traveler.ui.AndroidXMvpAppCompatFragment

class CitiesFragment : AndroidXMvpAppCompatFragment(), CitiesMvpView {

    @InjectPresenter
    internal lateinit var citiesPresenter: CitiesPresenter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("fragment_lifecycle", "citiesFragment#onCreate")  //TODO delete me
        Log.i("fragment_lifecycle", "presenter $citiesPresenter")  //TODO delete me
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.cities_recycler_view)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = TODO()
        }
    }
}
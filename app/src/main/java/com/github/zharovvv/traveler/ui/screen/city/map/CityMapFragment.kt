package com.github.zharovvv.traveler.ui.screen.city.map

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.transition.TransitionInflater
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.github.zharovvv.traveler.R
import com.github.zharovvv.traveler.ui.AndroidXMvpAppCompatFragment
import com.github.zharovvv.traveler.ui.model.Widget

class CityMapFragment : AndroidXMvpAppCompatFragment(), CityMapMvpView {

    lateinit var sourceCityWidget: Widget

    @InjectPresenter
    internal lateinit var cityMapPresenter: CityMapPresenter

    private lateinit var rootContainer: ViewGroup
    private lateinit var cityImageView: ImageView
    private lateinit var cityNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("fragment_lifecycle", "presenter $cityMapPresenter")
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("fragment_lifecycle", "cityMapFragment($this)#onCreateView")
        container?.let { rootContainer = it }
        return inflater.inflate(R.layout.fragment_city_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("fragment_lifecycle", "cityMapFragment($this)#onViewCreated")
        cityImageView = view.findViewById(R.id.city_image_view)
        ViewCompat.setTransitionName(cityImageView, "city_image_view")
        cityNameTextView = view.findViewById(R.id.city_name_text_view)
        if (savedInstanceState == null) {
            cityMapPresenter.initLoad(sourceCityWidget)
        }
        postponeEnterTransition()
    }

    override fun bindCityMap(cityMapWidget: Widget) {
        Log.i("Moxy", "CityMapMvpView#bindCityMap")
        cityNameTextView.text = cityMapWidget.title
        cityMapWidget.imageUrl?.let {
            Glide.with(cityImageView)
                .load(it)
                .apply {
                    RequestOptions().dontTransform()
                }
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                })
                .into(cityImageView)
        }
    }

}
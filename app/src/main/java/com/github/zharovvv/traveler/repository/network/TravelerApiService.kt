package com.github.zharovvv.traveler.repository.network

import com.github.zharovvv.traveler.repository.model.City
import com.github.zharovvv.traveler.repository.model.CityMap
import com.github.zharovvv.traveler.repository.model.Place
import com.github.zharovvv.traveler.repository.model.Route
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TravelerApiService {

    @GET("/cities?lastCityId={lastCityId}&limit={limit}")
    fun getCities(
        @Path("lastCityId") lastCityId: String,
        @Path("limit") limit: String
    ): Observable<List<City>>

    @GET("/cities/{cityId}/map")
    fun getCityMap(@Path("cityId") cityId: String): Observable<CityMap>

    @GET("/cities/{cityId}/places")
    fun getPlacesOfCity(@Path("cityId") cityId: String): Observable<List<Place>>

    @GET("/cities/{cityId}/routes")
    fun getRoutesOfCity(@Path("cityId") cityId: String): Observable<List<Route>>

    @GET("/places/{placeId}")
    fun getPlace(@Path("placeId") placeId: String): Observable<Place>

    @GET("/routes/{routeId}")
    fun getRoute(@Path("routeId") routeId: String): Observable<Route>
}
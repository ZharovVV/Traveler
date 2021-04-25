package com.github.zharovvv.traveler.repository.network

import com.github.zharovvv.traveler.repository.model.City
import com.github.zharovvv.traveler.repository.model.CityMap
import com.github.zharovvv.traveler.repository.model.Place
import com.github.zharovvv.traveler.repository.model.Route
import io.reactivex.Observable
import java.util.*

class TravelerApiServiceStubImpl : TravelerApiService {

    companion object {
        private val CITIES: Map<Long, City> = StubCityGenerator.generate()
        private val PLACES: Map<Long, Place> = StubPlaceGenerator.generate(CITIES)
        private val ROUTES: Map<Long, Route> = StubRouteGenerator.generate(CITIES, PLACES)
        private val CITY_MAPS: Map<Long, CityMap> =
            StubCityMapGenerator.generate(CITIES, PLACES, ROUTES)

        private const val DELAY = 3000L
    }


    override fun getCities(lastCityId: String, limit: String): Observable<List<City>> {
        Thread.sleep(DELAY)
        return Observable.just(
            CITIES.asSequence()
                .map { it.value }
                .sortedBy { it.id }
                .filter { it.id > lastCityId.toLong() && it.id <= lastCityId.toLong() + limit.toLong() }
                .toList()
        )
    }

    override fun getCityMap(cityId: String): Observable<CityMap> {
        Thread.sleep(DELAY)
        return Observable.just(
            CITY_MAPS[cityId.toLong()]
        )
    }

    override fun getPlacesOfCity(cityId: String): Observable<List<Place>> {
        Thread.sleep(DELAY)
        return Observable.just(
            PLACES.asSequence()
                .filter { it.value.cityId == cityId.toLong() }
                .map { it.value }
                .toList()
        )
    }

    override fun getRoutesOfCity(cityId: String): Observable<List<Route>> {
        Thread.sleep(DELAY)
        return Observable.just(
            ROUTES.asSequence()
                .filter { it.value.cityId == cityId.toLong() }
                .map { it.value }
                .toList()
        )
    }

    override fun getPlace(placeId: String): Observable<Place> {
        Thread.sleep(DELAY)
        return Observable.just(PLACES[placeId.toLong()])
    }

    override fun getRoute(routeId: String): Observable<Route> {
        Thread.sleep(DELAY)
        return Observable.just(
            ROUTES[routeId.toLong()]
        )
    }

    class StubCityGenerator {

        companion object {
            fun generate(): Map<Long, City> {
                val longRange: LongRange = 0L..50L
                return longRange.asSequence()
                    .map { id -> generateCity(id) }
                    .associateBy { city -> city.id }
            }

            private fun generateCity(cityId: Long): City {
                return City(
                    cityId,
                    "Город $cityId",
                    "Заглушечная страна города $cityId, Заглушечный регион города $cityId",
                    "Описание города $cityId",
                    "https://img-fotki.yandex.ru/get/6847/30348152.18f/0_8087f_f83cc27b_orig"
                )
            }
        }
    }

    class StubPlaceGenerator {
        companion object {

            fun generate(cities: Map<Long, City>): Map<Long, Place> {
                return cities.asSequence()
                    .flatMap { entry: Map.Entry<Long, City> ->
                        generatePlacesForCity(entry.key).asSequence()
                    }
                    .associateBy { place -> place.id }
            }

            private fun generatePlacesForCity(cityId: Long): List<Place> {
                val longRange = 0..10L
                return longRange.asSequence()
                    .map { id -> generatePlace(id, cityId) }
                    .toList()
            }

            private fun generatePlace(placeId: Long, cityId: Long): Place {
                val rewritedPlaceId = Objects.hash(placeId, cityId).toLong()
                return Place(
                    rewritedPlaceId,
                    cityId,
                    "Место $rewritedPlaceId",
                    "Описание места $rewritedPlaceId",
                    "Координаты места $rewritedPlaceId",
                    "https://img-fotki.yandex.ru/get/6847/30348152.18f/0_8087f_f83cc27b_orig"
                )
            }
        }
    }

    class StubRouteGenerator {
        companion object {

            fun generate(cities: Map<Long, City>, places: Map<Long, Place>): Map<Long, Route> {
                return cities.asSequence()
                    .flatMap { entry: Map.Entry<Long, City> ->
                        val cityId = entry.key
                        val placesInCity: List<Place> = places.asSequence()
                            .filter { placeEntry: Map.Entry<Long, Place> -> placeEntry.value.cityId == cityId }
                            .map { placeEntry -> placeEntry.value }
                            .toList()
                        generateRoutesForCity(cityId, placesInCity).asSequence()
                    }
                    .associateBy { route -> route.id }
            }

            private fun generateRoutesForCity(
                cityId: Long,
                places: List<Place>
            ): List<Route> {
                val longRange = 0..10L
                return longRange.asSequence()
                    .map { id -> generateRoute(id, cityId, places) }
                    .toList()
            }

            private fun generateRoute(routeId: Long, cityId: Long, places: List<Place>): Route {
                val rewritedRouteId = Objects.hash(routeId, cityId).toLong()
                return Route(
                    rewritedRouteId,
                    cityId,
                    "Маршрут $rewritedRouteId",
                    "Описание маршрута $rewritedRouteId",
                    places
                )
            }
        }
    }

    class StubCityMapGenerator {
        companion object {
            fun generate(
                cities: Map<Long, City>,
                places: Map<Long, Place>,
                routes: Map<Long, Route>
            ): Map<Long, CityMap> {
                return cities.asSequence()
                    .associateBy(
                        { cityEntry -> cityEntry.key },
                        { cityEntry ->
                            CityMap(
                                cityEntry.value,
                                places.asSequence()
                                    .filter { placeEntry -> placeEntry.value.cityId == cityEntry.key }
                                    .map { it.value }
                                    .toList(),
                                routes.asSequence()
                                    .filter { routeEntry -> routeEntry.value.cityId == cityEntry.key }
                                    .map { it.value }
                                    .toList()
                            )
                        }
                    )
            }
        }
    }
}
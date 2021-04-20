package com.github.zharovvv.traveler.repository.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class Interceptors {

    @Singleton
    class CacheInterceptor
    @Inject constructor(
        private val networkChecker: NetworkChecker
    ) : Interceptor {

        companion object {
            private const val MAX_AGE: Long = 60 * 60 //seconds
            private const val MAX_STALE: Long = 60 * 60 * 24 * 7 //seconds
        }

        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = if (networkChecker.check()) {
                request.newBuilder()
                    /**
                     * Если есть интернет, получите кеш, который хранился [MAX_AGE] секунд назад.
                     * Если кеш старше [MAX_AGE] секунд, то сбросьте его,
                     * и указать ошибку при получении ответа.
                     * За это поведение отвечает атрибут «max-age».
                     */
                    .header("Cache-Control", "public, max-age=$MAX_AGE")
                    .build()
            } else {
                request.newBuilder()
                    /**
                     * Если нет интернета, получите кеш, который хранился [MAX_STALE] секунд назад.
                     * Если кеш старше [MAX_STALE] секунд, то выбросьте его,
                     * и указать ошибку при получении ответа.
                     * Атрибут max-stale отвечает за такое поведение.
                     * Атрибут 'only-if-cached' указывает, что новые данные не извлекаются; получить только кеш.
                     */
                    .header("Cache-Control", "public, only-if-cached, max-stale=$MAX_STALE")
                    .build()
            }
            return chain.proceed(request)
        }
    }
}

package com.example.cliwaves.network.api

import com.example.cliwaves.data.RemoteLocation
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
        const val API_KEY = "68a64ac06b984d388f4153436242411"
    }

    @GET("search.json")
    suspend fun searchLocation(
        @Query("key") key: String = API_KEY,
        @Query("q") query: String
    ): retrofit2.Response<List<RemoteLocation>>

}
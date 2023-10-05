package com.guijarro.weatherapp.data.rest

import com.guijarro.weatherapp.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    /**
     * This methods sends an HTTP GET request to the server to retrieve data of a specified type.
     * @param city The city query to retrieve.
     * @param lat The lat query to retrieve.
     * @param lon The lon query to retrieve.
     * @param apiKey The APIKEY as unique identifier used to authenticate and authorize a user.
     * @return A `Response` object wrapping a `WeatherResponse` object.
     */
    @GET(WEATHER_PATH)
    suspend fun getCityWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = API_KEY
    ): Response<WeatherResponse>

    @GET(WEATHER_PATH)
    suspend fun getLocationWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = API_KEY
    ): Response<WeatherResponse>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        private const val WEATHER_PATH = "data/2.5/weather"
        private const val API_KEY = "801d6fa363152ff46e7978d69a4bad07"
    }

}
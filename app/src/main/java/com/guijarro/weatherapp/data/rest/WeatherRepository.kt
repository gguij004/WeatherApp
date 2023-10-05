package com.guijarro.weatherapp.data.rest

import com.guijarro.weatherapp.domain.WeatherDomain
import com.guijarro.weatherapp.domain.mapToDomainItems
import com.guijarro.weatherapp.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface WeatherRepository {
    fun getCityWeather(city: String?): Flow<UIState<WeatherDomain>>
    fun getLocationWeather(lat: Double, lon: Double): Flow<UIState<WeatherDomain>>
}

class WeatherRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi,
    private val ioDispatcher: CoroutineDispatcher,
) : WeatherRepository {

    override fun getCityWeather(city: String?): Flow<UIState<WeatherDomain>> = flow {
        city?.let {
            emit(UIState.LOADING)

            try {
                val response = serviceApi.getCityWeather(city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(UIState.SUCCESS(it.mapToDomainItems()))
                    } ?: throw Exception("Response is null")
                } else {
                    throw Exception("Failure response")
                }
            } catch (e: Exception) {
                emit(UIState.ERROR(e))
            }
        }
    }.flowOn(ioDispatcher)

    override fun getLocationWeather(lat: Double, lon: Double): Flow<UIState<WeatherDomain>> = flow {

        emit(UIState.LOADING)

        try {
            val response = serviceApi.getLocationWeather(lat, lon)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it.mapToDomainItems()))
                } ?: throw Exception("Response is null")
            } else {
                throw Exception("Failure response")
            }
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }

    }.flowOn(ioDispatcher)
}


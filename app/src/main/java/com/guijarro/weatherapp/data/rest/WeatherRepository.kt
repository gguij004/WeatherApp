package com.guijarro.weatherapp.data.rest

import com.guijarro.weatherapp.data.database.WeatherDAO
import com.guijarro.weatherapp.data.database.WeatherTable
import com.guijarro.weatherapp.data.database.toDomainTable
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
    // Local
    suspend fun insertWeather(weather: WeatherDomain)
    suspend fun deleteWeather(weather: WeatherTable)
    suspend fun getWeather(): UIState<WeatherTable>
}

class WeatherRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi,
    private val ioDispatcher: CoroutineDispatcher,
    private val weatherDAO: WeatherDAO
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


    //Local
    override suspend fun insertWeather(weather: WeatherDomain) {
        try {
            val weatherTable = weather.toDomainTable()
            weatherDAO.insertWeather(weatherTable)
        } catch (e: Exception) {
            e.message
        }
    }

    override suspend fun deleteWeather(weather: WeatherTable) {
        try {
            weatherDAO.deleteWeather(weather)
        } catch (e: Exception) {
            e.message
        }
    }

    override suspend fun getWeather(): UIState<WeatherTable> {
        return try {
            UIState.SUCCESS(weatherDAO.getWeather())
        } catch (e: Exception) {
            UIState.ERROR(e)
        }
    }

}
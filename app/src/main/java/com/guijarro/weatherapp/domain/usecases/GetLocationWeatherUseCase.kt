package com.guijarro.weatherapp.domain.usecases

import com.guijarro.weatherapp.data.rest.WeatherRepository
import com.guijarro.weatherapp.domain.WeatherDomain
import com.guijarro.weatherapp.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocationWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(lat: Double, lon: Double): Flow<UIState<WeatherDomain>> = flow {

        weatherRepository.getLocationWeather(lat, lon).collect {
            emit(it)
        }
    }
}
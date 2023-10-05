package com.guijarro.weatherapp.presentation.navigation

sealed class Routes(val route: String) {
    object WeatherScreen : Routes("Weather")
    object WeatherDetails : Routes("WeatherDetails")
}

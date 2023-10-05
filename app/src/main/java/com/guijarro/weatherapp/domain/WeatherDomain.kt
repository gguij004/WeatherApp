package com.guijarro.weatherapp.domain

import com.guijarro.weatherapp.data.model.Clouds
import com.guijarro.weatherapp.data.model.Coord
import com.guijarro.weatherapp.data.model.Main
import com.guijarro.weatherapp.data.model.Sys
import com.guijarro.weatherapp.data.model.Weather
import com.guijarro.weatherapp.data.model.WeatherResponse
import com.guijarro.weatherapp.data.model.Wind

data class WeatherDomain(
    val base: String? = null,
    val clouds: Clouds? = null,
    val cod: Int? = null,
    val coord: Coord? = null,
    val dt: Int? = null,
    val id: Int? = null,
    val main: Main? = null,
    val name: String? = null,
    val sys: Sys? = null,
    val timezone: Int? = null,
    val visibility: Int? = null,
    val weather: List<Weather>? = null,
    val wind: Wind? = null
)

fun WeatherResponse?.mapToDomainItems(): WeatherDomain =
    WeatherDomain(
        base = this?.base,
        clouds = this?.clouds,
        cod = this?.cod,
        coord = this?.coord,
        dt = this?.dt,
        id = this?.id,
        main = this?.main,
        name = this?.name,
        sys = this?.sys,
        timezone = this?.timezone,
        visibility = this?.visibility,
        weather = this?.weather,
        wind = this?.wind
    )

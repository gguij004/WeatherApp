package com.guijarro.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WeatherTable::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDAO

}
package com.guijarro.weatherapp.data.database

import androidx.room.*

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(vararg weather: WeatherTable)

    @Delete
    suspend fun deleteWeather(vararg weather: WeatherTable)

    @Query("SELECT * FROM weather")
    suspend fun getWeather(): WeatherTable

}
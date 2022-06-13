package com.franciscostanleyvasconceloszelaya.weather.mainModule.model

import com.franciscostanleyvasconceloszelaya.weather.common.entities.WeatherForecastEntity

class MainRepository {
    private val remoteDatabase = RemoteDatabase()

    suspend fun getWeatherAndForecast(
        lat: Double,
        long: Double,
        appId: String,
        exclude: String,
        units: String,
        lang: String
    ): WeatherForecastEntity =
        remoteDatabase.getWeatherForecastByCoordinates(lat, long, appId, exclude, units, lang)
}
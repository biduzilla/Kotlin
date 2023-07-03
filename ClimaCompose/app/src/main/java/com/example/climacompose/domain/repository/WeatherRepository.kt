package com.example.climacompose.domain.repository

import com.example.climacompose.domain.util.Resource
import com.example.climacompose.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}
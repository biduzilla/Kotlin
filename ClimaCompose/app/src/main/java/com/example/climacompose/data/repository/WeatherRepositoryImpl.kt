package com.example.climacompose.data.repository

import com.example.climacompose.data.mappers.toWeatherDataInfo
import com.example.climacompose.data.remote.WeatherApi
import com.example.climacompose.domain.repository.WeatherRepository
import com.example.climacompose.domain.util.Resource
import com.example.climacompose.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherDataInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Error Desconhecido.")
        }
    }
}
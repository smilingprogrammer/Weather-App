package com.example.weatherapp.api

import com.example.weatherapp.api.Data.City
import javax.inject.Inject

class ApiServiceImp @Inject constructor(private val apiService: ApiService){

    suspend fun getCityData(city: String, appid:String):City = apiService.getCityData(city, appid)
}
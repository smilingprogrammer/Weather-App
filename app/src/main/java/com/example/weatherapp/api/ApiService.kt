package com.example.weatherapp.api

import com.example.weatherapp.api.Model.City
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("wether/")
    suspend fun getCityData(@Query("q") q:String, @Query("appid") appid: String): City
}
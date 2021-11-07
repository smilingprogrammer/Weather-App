package com.example.weatherapp.api

import com.example.weatherapp.api.Data.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class weatherRespository @Inject constructor(private val apiServiceImp: ApiServiceImp) {

    fun getCityData(city: String) : Flow<City> = flow {
        val response = apiServiceImp.getCityData(city, "")
        emit(response)
    }.flowOn(Dispatchers.IO)
        .conflate()
}
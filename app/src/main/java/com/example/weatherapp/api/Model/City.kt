package com.example.weatherapp.api.Model

data class City(
    val weather: ArrayList<Weather>,
    val main: Main,
    val wind: Wind,
    val name: String
)

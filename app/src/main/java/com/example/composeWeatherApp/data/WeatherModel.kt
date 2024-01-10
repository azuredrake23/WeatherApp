package com.example.composeWeatherApp.data

data class WeatherModel(
    val city: String = "",
    val time: String = "",
    val currentTemp: String = "0.0",
    val condition: String = "",
    val icon: String = "",
    val maxTemp: String = "0.0",
    val minTemp: String = "0.0",
    val hours: String = ""
)

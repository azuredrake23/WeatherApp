package com.example.composeWeatherApp.data.models

data class SearchModel(val getList: List<WeatherModel> = listOf(WeatherModel()), val state: Boolean = false)

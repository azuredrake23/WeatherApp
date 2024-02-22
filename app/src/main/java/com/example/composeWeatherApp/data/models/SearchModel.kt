package com.example.composeWeatherApp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchModel(val list: List<WeatherModel> = listOf(WeatherModel()), val state: Boolean = false): Parcelable

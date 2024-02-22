package com.example.composeWeatherApp.utils

sealed class TimesOfDay {
    object Morning : TimesOfDay()
    object Day : TimesOfDay()
    object Evening : TimesOfDay()
    object Night : TimesOfDay()
}
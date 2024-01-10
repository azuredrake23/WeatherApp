package com.example.composeWeatherApp.data.utils

class SearchFilter (private val list: List<String>) {

    fun search(text: String): List<String> {
        return list.filter {
            it.lowercase().startsWith(text.lowercase())
        }
    }
}
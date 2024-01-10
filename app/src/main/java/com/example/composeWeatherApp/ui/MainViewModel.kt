package com.example.composeWeatherApp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.composeWeatherApp.data.utils.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(context: Application): AndroidViewModel(context) {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _weatherList = MutableStateFlow(listOf<String>())


    var weatherList = PreferencesManager(context).getList("searchList")
    var currentWeatherList = weatherList

}
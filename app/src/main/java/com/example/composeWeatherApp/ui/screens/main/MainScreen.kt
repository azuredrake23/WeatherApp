package com.example.composeWeatherApp.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeWeatherApp.data.models.WeatherModel
import com.example.composeWeatherApp.ui.screens.MainViewModel
import com.example.composeWeatherApp.ui.ui_addons.MainCard
import com.example.composeWeatherApp.ui.ui_addons.TabLayout

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    responseResultList: List<WeatherModel>,
    currentDay: Int,
    onPullRefresh: () -> Unit,
    onNavigateToSearchScreen: () -> Unit
) {
    with(mainViewModel) {
        MainScreenContent(
            responseResultList = responseResultList,
            currentDay = responseResultList[currentDay],
            onPullRefresh = onPullRefresh,
            onNavigateToSearchScreen = onNavigateToSearchScreen,
            onUpdateCurrentDay = { index ->
                updateCurrentDayIndex(index)
            }
        )
    }
}

@Composable
fun MainScreenContent(
    responseResultList: List<WeatherModel>,
    currentDay: WeatherModel,
    onPullRefresh: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onUpdateCurrentDay: (Int) -> Unit
) {
    Column (modifier = Modifier.fillMaxSize()) {
        MainCard(
            currentDay = currentDay,
            onPullRefresh = onPullRefresh,
            onNavigateToSearchScreen = onNavigateToSearchScreen
        )
        TabLayout(
            daysList = responseResultList,
            currentDay = currentDay,
            onUpdateCurrentDay = onUpdateCurrentDay
        )
    }
}

@Preview
@Composable
fun MainScreenContentPreview() {
    MainScreenContent(
        responseResultList = listOf(WeatherModel()),
        currentDay = WeatherModel(),
        onPullRefresh = {},
        onNavigateToSearchScreen = {},
        onUpdateCurrentDay = {}
    )
}
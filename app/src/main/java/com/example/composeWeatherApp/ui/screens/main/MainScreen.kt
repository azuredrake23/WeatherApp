package com.example.composeWeatherApp.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.composeWeatherApp.data.models.SearchModel
import com.example.composeWeatherApp.data.models.WeatherModel
import com.example.composeWeatherApp.ui.app.CommonScaffold
import com.example.composeWeatherApp.ui.app.LoadedBackgroundImage
import com.example.composeWeatherApp.ui.screens.MainViewModel
import com.example.composeWeatherApp.ui.ui_addons.MainCard
import com.example.composeWeatherApp.ui.ui_addons.SnackBar
import com.example.composeWeatherApp.ui.ui_addons.TabLayout
import com.example.composeWeatherApp.utils.Routes

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    drawerState: DrawerState,
    route: String
) {
    with(mainViewModel) {
        CommonScaffold(
            navController = navController,
            drawerState = drawerState,
            route = route,
            content = {
                val responseResult by responseResult.collectAsStateWithLifecycle()
                val currentDayIndex by currentDayIndex.collectAsStateWithLifecycle()

                LoadedBackgroundImage(mainViewModel)

                MainScreenContent(
                    responseResultList = responseResult.list,
                    currentDay = responseResult.list[currentDayIndex],
                    onPullRefresh = {
//                getResponseResult(context, cityName)
                    },
                    onNavigateToSearchScreen = {
                        navController.navigate(Routes.SEARCH_SCREEN)
                    },
                    onUpdateCurrentDay = { index ->
                        updateCurrentDayIndex(index)
                    }
                )

                SnackBar(responseResult.state)
            })
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
    Column(modifier = Modifier.fillMaxSize()) {
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
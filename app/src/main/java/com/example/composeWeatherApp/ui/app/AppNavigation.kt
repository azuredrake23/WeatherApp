package com.example.composeWeatherApp.ui.app

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.composeWeatherApp.R
import com.example.composeWeatherApp.ui.screens.MainViewModel
import com.example.composeWeatherApp.ui.screens.info.InfoScreen
import com.example.composeWeatherApp.ui.screens.main.MainScreen
import com.example.composeWeatherApp.ui.screens.search.SearchScreen
import com.example.composeWeatherApp.utils.Extentions.toTimeRange
import com.example.composeWeatherApp.utils.Routes
import com.example.composeWeatherApp.utils.TimesOfDay

@Composable
fun AppNavigation(
    context: Context,
    drawerState: DrawerState,
    mainViewModel: MainViewModel
) {
    NavHostNavigation(
        context = context,
        drawerState = drawerState,
        mainViewModel = mainViewModel
    )
}

@Composable
fun LoadedBackgroundImage(mainViewModel: MainViewModel) {
    val responseResult by mainViewModel.responseResult.collectAsStateWithLifecycle()
    Image(
        painter = painterResource(
            id = when (responseResult.list[0].time.toTimeRange()) {
                TimesOfDay.Night -> R.drawable.night
                TimesOfDay.Morning -> R.drawable.morning
                TimesOfDay.Day -> R.drawable.day
                TimesOfDay.Evening -> R.drawable.evening
            }
        ),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.8f),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun NavHostNavigation(
    context: Context,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState,
    mainViewModel: MainViewModel
) {
    NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.MAIN_SCREEN) {
            MainScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                drawerState = drawerState,
                route = it.destination.route.toString()
            )
        }

        composable(Routes.SEARCH_SCREEN) {
            SearchScreen(
                context = context,
                navController = navController,
                mainViewModel = mainViewModel,
                drawerState = drawerState,
                route = it.destination.route.toString()
            )
        }

        composable(Routes.INFO_SCREEN) {
            InfoScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                drawerState = drawerState,
                route = it.destination.route.toString()
            )
        }
    }
}
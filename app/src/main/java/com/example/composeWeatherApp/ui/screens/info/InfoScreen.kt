package com.example.composeWeatherApp.ui.screens.info

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.composeWeatherApp.ui.app.CommonScaffold
import com.example.composeWeatherApp.ui.app.LoadedBackgroundImage
import com.example.composeWeatherApp.ui.screens.MainViewModel

@Composable
fun InfoScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    drawerState: DrawerState,
    route: String
) {
    CommonScaffold(
        navController = navController,
        drawerState = drawerState,
        route = route,
        content = {
            LoadedBackgroundImage(mainViewModel)
        })
}

@Composable
fun InfoScreenContent() {

}

@Preview
@Composable
fun InfoScreenPreview() {

}
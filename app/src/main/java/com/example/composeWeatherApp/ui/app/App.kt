package com.example.composeWeatherApp.ui.app

import android.content.Context
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.composeWeatherApp.data.GetData
import com.example.composeWeatherApp.ui.screens.MainViewModel

@Composable
fun App(
    context: Context,
    mainViewModel: MainViewModel
) {
    GetData(context, mainViewModel)

    AppComponents(
        context = context,
        mainViewModel = mainViewModel
    )
}

@Composable
fun AppComponents(
    context: Context,
    mainViewModel: MainViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawerContent(drawerState)
        },
        drawerState = drawerState,
        content = {
            AppNavigation(
                context = context,
                drawerState = drawerState,
                mainViewModel = mainViewModel
            )
        }
    )
}
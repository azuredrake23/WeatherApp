package com.example.composeWeatherApp.ui.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composeWeatherApp.utils.Routes
import kotlinx.coroutines.launch


@Composable
fun ScaffoldViewStateValues(
    route: String,
    navController: NavController,
    drawerState: DrawerState,
    scaffoldViewState: MutableState<ScaffoldViewState>
) {
    scaffoldViewState.value = ScaffoldViewState(
        topBarActions = {
            ScaffoldTopBarActions(navController = navController, drawerState = drawerState, route = route)
        },
        bottomBarActions = {
            ScaffoldBottomBarActions(navController = navController, route = route)
        },
        topAppBarTitle = null
    )
}

@Composable
fun ScaffoldTopBarActions(navController: NavController, drawerState: DrawerState, route: String) {
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {
        when (route) {
            Routes.MAIN_SCREEN -> {
                IconButton(onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu Icon",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
            }

            Routes.SEARCH_SCREEN -> {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back Icon",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
            }

            Routes.INFO_SCREEN -> {
                IconButton(onClick = {
                    navController.navigate(Routes.MAIN_SCREEN)
                }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back Icon",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ScaffoldBottomBarActions(navController: NavController, route: String) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceAround
    ) {
        when (route) {
            Routes.MAIN_SCREEN -> {
                IconButton(onClick = { navController.navigate(Routes.MAIN_SCREEN) }) {
                    Icon(
                        imageVector = Icons.Rounded.Home,
                        contentDescription = "Home Icon",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
                IconButton(onClick = { navController.navigate(Routes.INFO_SCREEN) }) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "Info Icon",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
            }

            Routes.SEARCH_SCREEN -> {

            }

            Routes.INFO_SCREEN -> {
                IconButton(onClick = { navController.navigate(Routes.MAIN_SCREEN) }) {
                    Icon(
                        imageVector = Icons.Rounded.Home,
                        contentDescription = "Home Icon",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
                IconButton(onClick = { navController.navigate(Routes.INFO_SCREEN) }) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "Info Icon",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}



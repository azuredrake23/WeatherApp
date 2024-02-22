package com.example.composeWeatherApp.ui.app

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composeWeatherApp.R
import com.example.composeWeatherApp.data.models.SearchModel
import com.example.composeWeatherApp.ui.screens.info.InfoScreen
import com.example.composeWeatherApp.ui.screens.main.MainScreen
import com.example.composeWeatherApp.ui.screens.search.SearchScreen
import com.example.composeWeatherApp.ui.screens.MainViewModel
import com.example.composeWeatherApp.ui.theme.LightBlue
import com.example.composeWeatherApp.utils.Constants
import com.example.composeWeatherApp.utils.Extentions.toTimeRange
import com.example.composeWeatherApp.utils.Routes
import com.example.composeWeatherApp.utils.TimesOfDay
import kotlinx.coroutines.launch

@Immutable
data class ScaffoldViewState(
    val topBarActions: @Composable RowScope.() -> Unit = {},
    val bottomBarActions: @Composable RowScope.() -> Unit = {},
    @StringRes val topAppBarTitle: Int? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    context: Context,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val scaffoldViewState = remember {
        mutableStateOf(ScaffoldViewState())
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawerContent(drawerState)
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        scaffoldViewState.value.topAppBarTitle?.let {
                            Text(text = stringResource(id = it))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = LightBlue,
                        titleContentColor = Color.Black,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    actions = scaffoldViewState.value.topBarActions
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.height(50.dp),
                    containerColor = LightBlue,
                    actions = scaffoldViewState.value.bottomBarActions
                )
            }
        ) {
            AppContent(
                context = context,
                navController = navController,
                drawerState = drawerState,
                mainViewModel = mainViewModel,
                scaffoldViewState = scaffoldViewState,
                scaffoldPadding = it
            )
        }
    }

}

@Composable
fun AppContent(
    context: Context,
    navController: NavHostController,
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    scaffoldViewState: MutableState<ScaffoldViewState>,
    scaffoldPadding: PaddingValues
) {
    with(mainViewModel) {
        val cityName by cityName.collectAsStateWithLifecycle()
        val responseResult by responseResult.collectAsStateWithLifecycle()

        getData(context, cityName)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
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
            Column(
                modifier = Modifier
                    .scrollable(rememberScrollState(), Orientation.Vertical, true)
            ) {
                AppNavigation(
                    context = context,
                    navController = navController,
                    drawerState = drawerState,
                    mainViewModel = mainViewModel,
                    scaffoldViewState = scaffoldViewState,
                    responseResult = responseResult,
                    cityName = cityName
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    context: Context,
    navController: NavHostController,
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    scaffoldViewState: MutableState<ScaffoldViewState>,
    responseResult: SearchModel,
    cityName: String
) {
    with (mainViewModel) {
        val currentDay by currentDay.collectAsStateWithLifecycle()
        val searchList by searchList.collectAsStateWithLifecycle()

        NavHost(navController = navController, startDestination = Routes.MAIN_FRAGMENT) {
            navigation(
                route = Routes.MAIN_FRAGMENT,
                startDestination = Routes.MAIN_SCREEN
            ) {
                composable(Routes.MAIN_SCREEN) {
                    MainScreen(
                        mainViewModel = mainViewModel,
                        responseResultList = responseResult.list,
                        currentDay = currentDay,
                        onNavigateToSearchScreen = {
                            navController.navigate(Routes.SEARCH_SCREEN)
                        },
                        onPullRefresh = {
                            getData(context, cityName)
                        }
                    )
                    ScaffoldViewStateValues(
                        route = Routes.MAIN_SCREEN,
                        navController = navController,
                        drawerState = drawerState,
                        scaffoldViewState = scaffoldViewState
                    )
                }

                composable(Routes.SEARCH_SCREEN) {
                    SearchScreen(
                        mainViewModel = mainViewModel,
                        onClickSearch = {
                            if (responseResult.state) {
                                updateCityName(it)
                                addNewSearchItemInRoom(it)
                                if (searchList.size >= Constants.SEARCH_SIZE) {
                                    removeSearchItemInRoom(searchList[0])
                                }
                            }
                            navController.popBackStack()
                        },
                        searchList = searchList
                    )
                    ScaffoldViewStateValues(
                        route = Routes.SEARCH_SCREEN,
                        navController = navController,
                        drawerState = drawerState,
                        scaffoldViewState = scaffoldViewState
                    )
                }
            }

            composable(Routes.INFO_SCREEN) {
                InfoScreen(context = context)
                ScaffoldViewStateValues(
                    route = Routes.INFO_SCREEN,
                    navController = navController,
                    drawerState = drawerState,
                    scaffoldViewState = scaffoldViewState
                )
            }
        }
    }
}
package com.example.composeWeatherApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.composeWeatherApp.ui.screens.InfoScreen
import com.example.composeWeatherApp.ui.screens.main.MainScreen
import com.example.composeWeatherApp.ui.screens.main.MainViewModel
import com.example.composeWeatherApp.ui.screens.search.SearchScreen
import com.example.composeWeatherApp.ui.screens.search.SearchViewModel
import com.example.composeWeatherApp.ui.theme.JetpackComposeAppTheme
import com.example.composeWeatherApp.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val mainViewModel = hiltViewModel<MainViewModel>()
            val searchViewModel = hiltViewModel<SearchViewModel>()
            JetpackComposeAppTheme {
                NavHost(navController = navController, startDestination = Routes.MAIN_FRAGMENT){
                    navigation(route = Routes.MAIN_FRAGMENT, startDestination = Routes.MAIN_SCREEN){
                        composable(Routes.MAIN_SCREEN){
                            MainScreen(context = this@MainActivity, navController = navController, mainViewModel = mainViewModel, searchViewModel = searchViewModel)
                        }

                        composable(Routes.SEARCH_SCREEN){
                            SearchScreen(navController = navController, searchViewModel = searchViewModel)
                        }
                    }


                    composable(Routes.INFO_SCREEN){
                        InfoScreen(context = this@MainActivity)
                    }
                }
            }
        }
    }
}

//@Composable
//inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel (navController: NavController): T{
//val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
//    val parentEntry = remember(this){
//        navController.getBackStackEntry(navGraphRoute)
//    }
//    return hiltViewModel(parentEntry)
//}




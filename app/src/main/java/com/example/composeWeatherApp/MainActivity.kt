package com.example.composeWeatherApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.composeWeatherApp.ui.app.App
import com.example.composeWeatherApp.ui.screens.MainViewModel
import com.example.composeWeatherApp.ui.theme.JetpackComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            JetpackComposeAppTheme {
                App(context = this@MainActivity, navController = rememberNavController(), mainViewModel = hiltViewModel<MainViewModel>())
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




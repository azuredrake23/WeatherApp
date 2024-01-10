package com.example.composeWeatherApp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeWeatherApp.data.WeatherModel
import com.example.composeWeatherApp.data.utils.PreferencesManager
import com.example.composeWeatherApp.domain.getData
import com.example.composeWeatherApp.screens.MainCard
import com.example.composeWeatherApp.screens.TabLayout
import com.example.composeWeatherApp.ui.MainViewModel
import com.example.composeWeatherApp.ui.theme.JetpackComposeAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel = viewModel<MainViewModel>()

            JetpackComposeAppTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val currentDay = remember {
                    mutableStateOf(WeatherModel())
                }
//                val dialogState = remember {
//                    mutableStateOf(false)
//                }
                val city = remember {
                    mutableStateOf("London")
                }
//                val searchList by remember {
//                    mutableStateOf(
//
//                    )
//                }
//                if (dialogState.value) {
//                    DialogSearch(dialogState, onSubmit = {
//                        city.value = it
//                        getData(this@MainActivity, it, daysList, currentDay)
//                    })
//                }
                getData(this, city.value, daysList, currentDay)
                Image(
                    painter = painterResource(id = R.drawable.weather),
                    contentDescription = "im1",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.8f),
                    contentScale = ContentScale.Crop
                )
                Column {
                    MainCard(
                        currentDay,
                        mainViewModel.weatherList as MutableList<String>,
                        onClickSync = {
                            getData(this@MainActivity, city.value, daysList, currentDay)
                        },
                        onClickSearch = {
                            PreferencesManager(this@MainActivity).saveList("searchList", it)
                            if (getData(this@MainActivity, it, daysList, currentDay)) {
                                city.value = it
                            }
                        }
                    )
                    TabLayout(daysList, currentDay)
                }
            }
        }
    }
}




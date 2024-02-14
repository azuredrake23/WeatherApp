package com.example.composeWeatherApp.ui.screens.main

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.composeWeatherApp.R
import com.example.composeWeatherApp.data.models.SearchModel
import com.example.composeWeatherApp.data.models.WeatherModel
import com.example.composeWeatherApp.ui.screens.search.SearchViewModel
import com.example.composeWeatherApp.ui.ui_addons.MainCard
import com.example.composeWeatherApp.ui.ui_addons.TabLayout
import com.example.composeWeatherApp.utils.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    context: Context,
    navController: NavController,
    mainViewModel: MainViewModel,
    searchViewModel: SearchViewModel
) {
    with(searchViewModel) {
        val currentCityName by cityName.collectAsStateWithLifecycle()
        val responseResult by responseResult.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = Unit){
            mainViewModel.getData(context, searchViewModel, currentCityName)
        }

        MainScreenContent(
            responseResultList = responseResult.getList,
            onClickSync = {
                mainViewModel.getData(context, searchViewModel, currentCityName)
            },
            onNavigate = {
                navController.navigate(Routes.SEARCH_SCREEN)
            },
            onUpdateResponseResult = {
                updateResponseResult(responseResult.copy(getList = it))
            }
        )
    }

}

@Composable
fun MainScreenContent(
    responseResultList: List<WeatherModel>,
    onClickSync: () -> Unit,
    onNavigate: () -> Unit,
    onUpdateResponseResult: (List<WeatherModel>) -> Unit
) {
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
            currentDay = responseResultList[0],
            onClickSync = onClickSync,
            onNavigate = onNavigate
        )
        TabLayout(
            daysList = responseResultList,
            currentDay = responseResultList[0],
            onUpdateResponseResult = onUpdateResponseResult
        )
    }
}

@Preview
@Composable
fun MainScreenContentPreview(){
    MainScreenContent(
        responseResultList = listOf(WeatherModel()),
        onClickSync = {},
        onNavigate = {},
        onUpdateResponseResult = {}
    )
}
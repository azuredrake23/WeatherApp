package com.example.composeWeatherApp.ui.ui_addons

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TabRow
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.composeWeatherApp.R
import com.example.composeWeatherApp.data.models.WeatherModel
import com.example.composeWeatherApp.data.getWeatherByHours
import com.example.composeWeatherApp.data.models.ResponseState
import com.example.composeWeatherApp.data.models.SearchModel
import com.example.composeWeatherApp.ui.theme.LightTransparentBlue
import com.example.composeWeatherApp.utils.Constants
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun MainCard(
    currentDay: WeatherModel,
    onPullRefresh: () -> Unit,
    onNavigateToSearchScreen: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = CardDefaults.cardColors(containerColor = LightTransparentBlue),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = Modifier.padding(15.dp),
                        text = currentDay.time,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Text(
                        text = currentDay.city,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Text(
                        text = if (currentDay.currentTemp.isEmpty()) {
                            "${
                                currentDay.minTemp.toFloat().toInt()
                            }°C/${currentDay.maxTemp.toFloat().toInt()}°C"
                        } else "${currentDay.currentTemp.toFloat().toInt()}°C",
                        fontSize = 65.sp,
                        color = Color.White
                    )
                    Text(
                        text = currentDay.condition,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${
                            currentDay.minTemp.toFloat().toInt()
                        }°C/${currentDay.maxTemp.toFloat().toInt()}°C",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
                Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                    AsyncImage(
                        modifier = Modifier.size(150.dp),
                        model = "https:" + currentDay.icon,
                        contentDescription = "image2",
                        alignment = Alignment.Center
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onNavigateToSearchScreen) {
                    Icon(
                        painterResource(id = R.drawable.search),
                        contentDescription = "image3",
                        tint = Color.White
                    )
                }
                IconButton(onClick = onPullRefresh) {
                    Icon(
                        painterResource(id = R.drawable.sync),
                        contentDescription = "image4",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(
    daysList: List<WeatherModel>,
    currentDay: WeatherModel,
    onUpdateCurrentDay: (Int) -> Unit
) {
    val tabList = listOf(Constants.TAB_NAME_1, Constants.TAB_NAME_2)
    val daysTabIndex = tabList.indexOf(Constants.TAB_NAME_1)
    val hoursTabIndex = tabList.indexOf(Constants.TAB_NAME_2)
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(
                start = 12.dp,
                end = 12.dp
            )
            .clip(
                shape = RoundedCornerShape(5.dp)
            ),
        verticalArrangement = Arrangement.Top
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, pos)
                )
            },
            backgroundColor = LightTransparentBlue,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, itemName ->
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = itemName, color = Color.White)
                    }
                )
            }
        }
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .height(200.dp),
            verticalAlignment = Alignment.Top,
            count = tabList.size,
            state = pagerState
        ) { index ->
            val list = when (index) {
                daysTabIndex -> daysList
                hoursTabIndex -> getWeatherByHours(currentDay.hours)
                else -> daysList
            }
            HorizontalPagerListContent(
                list = list,
                onUpdateCurrentDay = onUpdateCurrentDay
            )
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SnackBar(responseState: ResponseState){
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    if (responseState is ResponseState.Failure) {
        Box(modifier = Modifier.fillMaxSize()) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message = responseState.error)
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}



package com.example.composeWeatherApp.ui.ui_addons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TabRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.composeWeatherApp.ui.screens.search.SearchViewModel
import com.example.composeWeatherApp.ui.theme.LightTransparentBlue
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun MainCard(
    currentDay: WeatherModel,
    onClickSync: () -> Unit,
    onNavigate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                        text = currentDay.time,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    AsyncImage(
                        modifier = Modifier.size(35.dp),
                        model = "https:" + currentDay.icon,
                        contentDescription = "image2"
                    )
                }
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
//                    SearchBar(
//                        onActiveChange = {
//
//                        }
//                    ) {
//
//                    }
                    IconButton(onNavigate) {
                        Icon(
                            painterResource(id = R.drawable.search),
                            contentDescription = "image3",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "${
                            currentDay.minTemp.toFloat().toInt()
                        }°C/${currentDay.maxTemp.toFloat().toInt()}°C",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    IconButton(onClick = {
                        onClickSync.invoke()
                    }) {
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

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(daysList: List<WeatherModel>, currentDay: WeatherModel, onUpdateResponseResult: (List<WeatherModel>) -> Unit) {
    val tabList = listOf("DAYS", "HOURS")
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
            )
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
                        Text(text = itemName)
                    }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->
            val list = when (index) {
                0 -> daysList
                1 -> getWeatherByHours(currentDay.hours)
                else -> daysList
            }
            MainList(daysList = list, onUpdateResponseResult = onUpdateResponseResult)
        }
    }
}
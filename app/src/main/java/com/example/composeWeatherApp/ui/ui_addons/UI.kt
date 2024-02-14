package com.example.composeWeatherApp.ui.ui_addons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.composeWeatherApp.ui.theme.LightTransparentBlue
import com.example.composeWeatherApp.data.models.WeatherModel
import com.example.composeWeatherApp.data.utils.SearchFilter
import com.example.composeWeatherApp.ui.theme.Silver
import com.example.composeWeatherApp.utils.Extentions.swapList

@Composable
fun MainList(daysList: List<WeatherModel>, onUpdateResponseResult: (List<WeatherModel>) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(daysList) { _, item ->
            ListItem(daysList = daysList, item = item, onUpdateResponseResult = onUpdateResponseResult)
        }
    }
}

@Composable
fun ListItem(daysList: List<WeatherModel>, item: WeatherModel, onUpdateResponseResult: (List<WeatherModel>) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                if (item.hours.isNotEmpty()) {
                    val newList = daysList.toMutableList()
                    newList.removeFirst()
                    newList.add(0, item)
                    onUpdateResponseResult.invoke(newList.toList())
                }
            },
        colors = CardDefaults.cardColors(containerColor = LightTransparentBlue),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.time,
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Text(
                    text = item.condition,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Text(
                text = if (item.currentTemp.isEmpty()) {
                    "${item.minTemp.toFloat().toInt()}°C/${item.maxTemp.toFloat().toInt()}°C"
                } else "${item.currentTemp.toFloat().toInt()}°C",
                color = Color.White,
                fontSize = 20.sp
            )
            AsyncImage(
                modifier = Modifier.size(35.dp),
                model = "https:${item.icon}",
                contentDescription = "image5"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(searchList: List<String>, onClickCloseIcon: (String) -> Unit, onClickSearch: (String) -> Unit, searchListOutOfBounds: () -> Unit) {
    val currentList = remember {
        mutableStateListOf<String>()
    }

    searchListOutOfBounds.invoke()

    var searchText by remember {
        mutableStateOf("")
    }

    var isActive by remember {
        mutableStateOf(false)
    }

    DockedSearchBar(
        query = searchText,
        onQueryChange = { text ->
            searchText = text
            if (text == " ") {
                searchText = ""
            }
            currentList.swapList(SearchFilter(searchList).search(text))
            SearchFilter(searchList).search(text)
        },
        onSearch = { text ->
            isActive = false
            onClickSearch(text)
        },
        active = isActive,
        onActiveChange = {
            isActive = it
            if (it) {
                currentList.swapList(searchList)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        placeholder = {
            Text(text = "Search...")
        },
        colors = SearchBarDefaults.colors(
            containerColor = Silver,
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black
            )
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (isActive) {
                Icon(
                    modifier = Modifier.clickable {
                        if (searchText.isNotEmpty()) {
                            searchText = ""
                        } else {
                            isActive = false
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon"
                )
            }
        }
    ) {
        LazyColumn {
            items(items = currentList.reversed()) { item ->
                Box(modifier = Modifier
                    .clickable {
                        currentList.swapList(SearchFilter(searchList).search(item))
                        isActive = false
                        searchText = item
                        onClickSearch(item)
                    }
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(4.dp))) {
                    Row(
                        modifier = Modifier.fillMaxWidth().background(color = Silver),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = item)
                        Icon(
                            modifier = Modifier.clickable {
                                onClickCloseIcon.invoke(item)
                                currentList.remove(item)
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon"
                        )
                    }
                }
            }
        }
    }
}
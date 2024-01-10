package com.example.composeWeatherApp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.composeWeatherApp.ui.theme.LightBlue
import com.example.composeWeatherApp.data.WeatherModel
import com.example.composeWeatherApp.data.utils.SearchFilter
import com.example.composeWeatherApp.ui.MainViewModel

@Composable
fun MainList(list: List<WeatherModel>, currentDay: MutableState<WeatherModel>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(list) { _, item ->
            ListItem(item = item, currentDay)
        }
    }
}

@Composable
fun ListItem(item: WeatherModel, currentDay: MutableState<WeatherModel>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                if (item.hours.isNotEmpty())
                    currentDay.value = item
            },
        colors = CardDefaults.cardColors(containerColor = LightBlue),
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
fun DialogSearch(dialogState: MutableState<Boolean>, onSubmit: (String) -> Unit) {
    val dialogText = remember {
        mutableStateOf("")
    }
    AlertDialog(onDismissRequest = {
        dialogState.value = false
    },
        confirmButton = {
            TextButton(onClick = {
                onSubmit(dialogText.value)
                dialogState.value = false
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                dialogState.value = false
            }) {
                Text(text = "Cancel")
            }
        },
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {

            }
            Text(text = "Введите название города:")
            TextField(value = dialogText.value, onValueChange = {
                dialogText.value = it
            })
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(list: MutableList<String>, onClickSearch: (String) -> Unit) {
    val mainViewModel = viewModel<MainViewModel>()

    var searchText by remember {
        mutableStateOf("")
    }

//    var currentList by remember {
//        mutableStateOf(list.toList())
//    }

    var isActive by remember {
        mutableStateOf(false)
    }

    SearchBar(
        query = searchText,
        onQueryChange = { text ->
            searchText = text
            if (text == " ") {
                searchText = ""
            }
//            mainViewModel.currentWeatherList = SearchFilter(mainViewModel.weatherList).search(text)
        },
        onSearch = { text ->
            isActive = false
            onClickSearch(text)
        },
        active = isActive,
        onActiveChange = {
            isActive = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        placeholder = {
            Text(text = "Search...")
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color.LightGray,
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = Color.White
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
            items(mainViewModel.currentWeatherList) { item ->
                Box(modifier = Modifier
                    .clickable {
                        mainViewModel.currentWeatherList = SearchFilter(mainViewModel.currentWeatherList).search(item)
                        isActive = false
                        searchText = item
                        onClickSearch(item)
                    }
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Text(text = item)
                }
            }
        }
    }
}
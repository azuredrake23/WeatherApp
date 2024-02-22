package com.example.composeWeatherApp.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeWeatherApp.ui.screens.MainViewModel
import com.example.composeWeatherApp.ui.ui_addons.Search
import com.example.composeWeatherApp.utils.Constants

@Composable
fun SearchScreen(
    mainViewModel: MainViewModel,
    onClickSearch: (String) -> Unit,
    searchList: List<String>
) {
    with(mainViewModel) {
        SearchScreenContent(
            searchList = searchList,
            onClickCloseIcon = { item ->
                removeSearchItemInRoom(item)
            },
            onClickSearch = onClickSearch
        )
    }
}

@Composable
fun SearchScreenContent(
    searchList: List<String>,
    onClickCloseIcon: (String) -> Unit,
    onClickSearch: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 30.sp,
            text = Constants.SEARCH_SCREEN_HEADER_NAME
        )
        Search(
            searchList = searchList,
            onClickCloseIcon = onClickCloseIcon,
            onClickSearch = onClickSearch
        )
    }
}

@Preview
@Composable
fun SearchScreenContentPreview() {
    SearchScreenContent(
        searchList = listOf(),
        onClickCloseIcon = { },
        onClickSearch = {}
    )
}
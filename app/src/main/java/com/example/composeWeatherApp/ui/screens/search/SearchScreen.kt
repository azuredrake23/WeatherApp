package com.example.composeWeatherApp.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.composeWeatherApp.data.models.SearchModel
import com.example.composeWeatherApp.data.utils.Constants
import com.example.composeWeatherApp.ui.ui_addons.Search

@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel) {
    with(searchViewModel) {
        val responseResult by responseResult.collectAsStateWithLifecycle(initialValue = SearchModel())
        val searchList by getListFromRoom.collectAsStateWithLifecycle(initialValue = listOf())
        SearchScreenContent(
            navController = navController,
            searchList = searchList,
            onClickCloseIcon = {
                removeSearchItemInRoom(it)
            },
            onClickSearch = {
                if (responseResult.state) {
                    updateCityName(it)
                }
                addNewSearchItemInRoom(it)
                navController.popBackStack()
            },
            searchListOutOfBounds = {
                if (searchList.size >= Constants.SEARCH_SIZE)
                    removeSearchItemInRoom(searchList[0])
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    navController: NavController,
    searchList: List<String>,
    onClickCloseIcon: (String) -> Unit,
    onClickSearch: (String) -> Unit,
    searchListOutOfBounds: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
//                Text(text = Constants.SEARCH_WINDOW_NAME)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Rounded.ArrowBack, "")
                    }
                }
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 30.sp,
                        text = Constants.SEARCH_SCREEN_HEADER_NAME
                    )
                    Search(
                        searchList = searchList,
                        onClickCloseIcon = onClickCloseIcon,
                        onClickSearch = onClickSearch,
                        searchListOutOfBounds = searchListOutOfBounds
                    )
                }
            }
        })
}

@Preview
@Composable
fun SearchScreenContentPreview() {
    SearchScreenContent(
        navController = NavController(LocalContext.current),
        searchList = listOf(),
        onClickCloseIcon = {},
        onClickSearch = {},
        searchListOutOfBounds = {})
}
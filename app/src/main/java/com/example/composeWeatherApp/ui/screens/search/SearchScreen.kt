package com.example.composeWeatherApp.ui.screens.search

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.composeWeatherApp.ui.app.CommonScaffold
import com.example.composeWeatherApp.ui.app.ScaffoldBottomBarActions
import com.example.composeWeatherApp.ui.app.ScaffoldTopBarActions
import com.example.composeWeatherApp.ui.screens.MainViewModel
import com.example.composeWeatherApp.ui.screens.main.MainScreenContent
import com.example.composeWeatherApp.ui.theme.LightBlue
import com.example.composeWeatherApp.ui.ui_addons.Search
import com.example.composeWeatherApp.ui.ui_addons.SnackBar
import com.example.composeWeatherApp.utils.Constants
import com.example.composeWeatherApp.utils.Routes

@Composable
fun SearchScreen(
    context: Context,
    navController: NavController,
    mainViewModel: MainViewModel,
    drawerState: DrawerState,
    route: String
) {
    with(mainViewModel) {
        val searchList by searchList.collectAsStateWithLifecycle()

        CommonScaffold(
            navController = navController,
            drawerState = drawerState,
            route = route,
            content = {
                SearchScreenContent(
                    searchList = searchList,
                    onClickCloseIcon = { item ->
                        removeSearchItemInRoom(item)
                    },
                    onClickSearch = {
                        updateCityName(it)
                        navController.popBackStack()
                    }
                )
            })
    }
}

@Stable
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
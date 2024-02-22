package com.example.composeWeatherApp.ui.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.composeWeatherApp.R
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerContent (drawerState: DrawerState){
    val scope = rememberCoroutineScope()

    ModalDrawerSheet {
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                painter = painterResource(
                    id = R.drawable.weather
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            IconButton(onClick = {
                scope.launch {
                    drawerState.close()
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = null
                )
            }
        }
    }
}
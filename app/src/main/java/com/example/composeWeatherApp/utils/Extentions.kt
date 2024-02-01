package com.example.composeWeatherApp.utils

import androidx.compose.runtime.snapshots.SnapshotStateList

object Extentions {
    fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
        clear()
        addAll(newList)
    }
}
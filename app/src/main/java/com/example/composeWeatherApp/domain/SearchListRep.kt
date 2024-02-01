package com.example.composeWeatherApp.domain

import com.example.composeWeatherApp.data.database.SearchItemModel
import com.example.composeWeatherApp.data.database.SearchListDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface SearchListRep {

    fun getList(): Flow<List<String>>

    fun addSearchItem(newItem: String)

    fun removeSearchItem(searchItem: String)
}
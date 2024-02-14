package com.example.composeWeatherApp.domain.repositories.searchListRep

import kotlinx.coroutines.flow.Flow

interface SearchListRep {

    fun getSearchList(): Flow<List<String>>

    suspend fun addSearchItem(newItem: String)

    suspend fun removeSearchItem(searchItem: String)
}
package com.example.composeWeatherApp.domain.repositories.searchListRep.usecases

import com.example.composeWeatherApp.domain.repositories.searchListRep.SearchListRep
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchListUseCase @Inject constructor (private val repository: SearchListRep) {

    fun getSearchList(): Flow<List<String>> = repository.getSearchList()

}
package com.example.composeWeatherApp.domain.repositories.searchListRep.usecases

import com.example.composeWeatherApp.domain.repositories.searchListRep.SearchListRep
import javax.inject.Inject

class RemoveSearchItemUseCase @Inject constructor (private val repository: SearchListRep) {

    suspend fun removeSearchItem(searchItem: String){
        repository.removeSearchItem(searchItem)
    }
}
package com.example.composeWeatherApp.domain.repositories.searchListRep.usecases

import com.example.composeWeatherApp.domain.repositories.searchListRep.SearchListRep
import javax.inject.Inject

class AddNewSearchItemUseCase @Inject constructor (private val repository: SearchListRep) {

    suspend fun addNewSearchItem(searchItem: String){
        repository.addSearchItem(searchItem)
    }
}
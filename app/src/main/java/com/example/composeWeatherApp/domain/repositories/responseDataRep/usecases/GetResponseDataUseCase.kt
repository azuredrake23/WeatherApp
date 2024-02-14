package com.example.composeWeatherApp.domain.repositories.responseDataRep.usecases

import com.example.composeWeatherApp.domain.repositories.responseDataRep.ResponseDataRep
import com.example.composeWeatherApp.domain.repositories.searchListRep.SearchListRep
import javax.inject.Inject

class GetResponseDataUseCase @Inject constructor (private val repository: ResponseDataRep) {

    suspend fun getResponseData(){
        repository.getResponseData()
    }
}
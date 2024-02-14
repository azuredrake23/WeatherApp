package com.example.composeWeatherApp.data.repositories_Impl

import com.example.composeWeatherApp.data.room_db.SearchItemModel
import com.example.composeWeatherApp.data.room_db.SearchListDao
import com.example.composeWeatherApp.domain.repositories.responseDataRep.ResponseDataRep
import com.example.composeWeatherApp.domain.repositories.searchListRep.SearchListRep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResponseDataRepImpl @Inject constructor(private val listDao: SearchListDao): ResponseDataRep {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun getResponseData(){
        coroutineScope.launch {

        }
    }

}
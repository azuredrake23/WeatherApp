package com.example.composeWeatherApp.data.repositories_Impl

import com.example.composeWeatherApp.data.room_db.SearchItemModel
import com.example.composeWeatherApp.data.room_db.SearchListDao
import com.example.composeWeatherApp.domain.repositories.searchListRep.SearchListRep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchListRepImpl (private val listDao: SearchListDao): SearchListRep {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun getSearchList(): Flow<List<String>> = listDao.getSearchList()

    override suspend fun addSearchItem(newItem: String) {
        coroutineScope.launch(Dispatchers.IO) {
            listDao.addSearchItem(SearchItemModel(newItem))
        }
    }

    override suspend fun removeSearchItem(searchItem: String) {
        coroutineScope.launch(Dispatchers.IO){
            listDao.removeSearchItem(searchItem)
        }
    }
}
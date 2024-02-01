package com.example.composeWeatherApp.data

import com.example.composeWeatherApp.data.database.SearchItemModel
import com.example.composeWeatherApp.data.database.SearchListDao
import com.example.composeWeatherApp.domain.SearchListRep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchListRepImpl @Inject constructor(private val listDao: SearchListDao): SearchListRep {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun getList(): Flow<List<String>> = listDao.getList()

    override fun addSearchItem(newItem: String) {
        coroutineScope.launch(Dispatchers.IO) {
            listDao.addSearchItem(SearchItemModel(newItem))
        }
    }

    override fun removeSearchItem(searchItem: String) {
        coroutineScope.launch(Dispatchers.IO){
            listDao.removeSearchItem(searchItem)
        }
    }
}
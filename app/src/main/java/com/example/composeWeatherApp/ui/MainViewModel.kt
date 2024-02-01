package com.example.composeWeatherApp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.composeWeatherApp.domain.SearchListRep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    context: Application,
    private val searchListRep: SearchListRep
    ): AndroidViewModel(context) {

    fun getList(): Flow<List<String>> = searchListRep.getList()

    fun addNewSearchItem(newItem: String){
        searchListRep.addSearchItem(newItem)
    }

    fun removeSearchItem(searchItem: String){
        searchListRep.removeSearchItem(searchItem)
    }

}
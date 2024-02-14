package com.example.composeWeatherApp.ui.screens.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeWeatherApp.data.models.SearchModel
import com.example.composeWeatherApp.data.utils.Constants
import com.example.composeWeatherApp.domain.repositories.searchListRep.usecases.AddNewSearchItemUseCase
import com.example.composeWeatherApp.domain.repositories.searchListRep.usecases.GetSearchListUseCase
import com.example.composeWeatherApp.domain.repositories.searchListRep.usecases.RemoveSearchItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSearchListUseCase: GetSearchListUseCase,
    private val addNewSearchItemUseCase: AddNewSearchItemUseCase,
    private val removeSearchItemUseCase: RemoveSearchItemUseCase
) : ViewModel() {

    val cityName = savedStateHandle.getStateFlow(Constants.CITY_NAME, Constants.DEFAULT_CITY_NAME)

    private val _responseResult = MutableStateFlow(SearchModel())
    val responseResult = _responseResult.asStateFlow()

    val getListFromRoom = getSearchListUseCase.getSearchList().stateIn(
        scope = viewModelScope,
        initialValue = listOf(),
        started = SharingStarted.WhileSubscribed(5000)
    )

    fun updateCityName(cityName: String) {
        viewModelScope.launch {
            savedStateHandle[Constants.CITY_NAME] = cityName
        }
    }

    fun updateResponseResult(result: SearchModel) {
        viewModelScope.launch {
            _responseResult.emit(result)
        }
    }

    fun addNewSearchItemInRoom(newItem: String) {
        viewModelScope.launch {
            addNewSearchItemUseCase.addNewSearchItem(newItem)
        }
    }

    fun removeSearchItemInRoom(searchItem: String) {
        viewModelScope.launch {
            removeSearchItemUseCase.removeSearchItem(searchItem)
        }
    }
}
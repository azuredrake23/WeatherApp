package com.example.composeWeatherApp.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.composeWeatherApp.data.getWeatherByDays
import com.example.composeWeatherApp.data.models.ResponseState
import com.example.composeWeatherApp.data.models.SearchModel
import com.example.composeWeatherApp.domain.repositories.searchListRep.usecases.AddNewSearchItemUseCase
import com.example.composeWeatherApp.domain.repositories.searchListRep.usecases.GetSearchListUseCase
import com.example.composeWeatherApp.domain.repositories.searchListRep.usecases.RemoveSearchItemUseCase
import com.example.composeWeatherApp.utils.Constants
import com.example.composeWeatherApp.utils.Constants.DATA_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSearchListUseCase: GetSearchListUseCase,
    private val addNewSearchItemUseCase: AddNewSearchItemUseCase,
    private val removeSearchItemUseCase: RemoveSearchItemUseCase
) : ViewModel() {

    val cityName = savedStateHandle.getStateFlow(Constants.CITY_NAME, Constants.DEFAULT_CITY_NAME)
    val responseResult =
        savedStateHandle.getStateFlow(Constants.SEARCH_RESPONSE_NAME, SearchModel())
    val currentDayIndex = savedStateHandle.getStateFlow(Constants.CURRENT_DAY_NAME, 0)

    val searchList = getSearchListUseCase.getSearchList().stateIn(
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
            savedStateHandle[Constants.SEARCH_RESPONSE_NAME] = result
        }
    }

    fun updateCurrentDayIndex(newCurrentDayIndex: Int) {
        viewModelScope.launch {
            savedStateHandle[Constants.CURRENT_DAY_NAME] = newCurrentDayIndex
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

    fun getWeatherResponseResult(context: Context, city: String = Constants.DEFAULT_CITY_NAME) {
        viewModelScope.launch {
            val url = "https://api.weatherapi.com/v1/forecast.json?key=$DATA_API_KEY" +
                    "&q=${city}" +
                    "&days=3" +
                    "&aqi=no" +
                    "&alerts=no"
            val queue = Volley.newRequestQueue(context)
            val stringRequest = StringRequest(
                Request.Method.GET,
                url, { response ->
                    addNewSearchItemInRoom(city)
                    if (searchList.value.size >= Constants.SEARCH_SIZE) {
                        removeSearchItemInRoom(searchList.value[0])
                    }
                    updateResponseResult(SearchModel(getWeatherByDays(response), ResponseState.Success))
                },
                { error ->
                    updateResponseResult(SearchModel(state = ResponseState.Failure(error.toString())))
                })
            queue.add(stringRequest)
        }
    }
}
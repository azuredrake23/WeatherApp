package com.example.composeWeatherApp.ui.screens.main

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.composeWeatherApp.data.API_KEY
import com.example.composeWeatherApp.data.getWeatherByDays
import com.example.composeWeatherApp.data.models.SearchModel
import com.example.composeWeatherApp.ui.screens.search.SearchViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    fun getData(context: Context, searchViewModel: SearchViewModel, city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val url = "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY" +
                    "&q=$city" +
                    "&days=3" +
                    "&aqi=no" +
                    "&alerts=no"
            val queue = Volley.newRequestQueue(context)
            var responseResult = SearchModel()
            val stringRequest = StringRequest(
                Request.Method.GET,
                url, { response ->
                    responseResult = SearchModel(getWeatherByDays(response), true)
                    searchViewModel.updateResponseResult(responseResult)
                },
                { error ->
                    searchViewModel.updateResponseResult(responseResult.copy(state = false))
                    Log.d("Volley Error", "Volley Error: $error")
                })
            queue.add(stringRequest)
        }

    }
}
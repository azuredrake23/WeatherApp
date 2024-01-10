package com.example.composeWeatherApp.domain

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.composeWeatherApp.data.WeatherModel
import org.json.JSONArray
import org.json.JSONObject

const val API_KEY = "d6425ef100db4ee68c3133507231812"

fun getData(context: Context, city: String, daysList: MutableState<List<WeatherModel>>, currentDay: MutableState<WeatherModel>): Boolean {
    var result = false
    val url = "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY" +
            "&q=$city" +
            "&days=3" +
            "&aqi=no" +
            "&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url, { response ->
            val list = getWeatherByDays(response)
            daysList.value = list
            currentDay.value = list[0]
            result = true
        },
        { error ->
            Log.d("Volley Error", "Volley Error: $error")
            result = false
        })
    queue.add(stringRequest)
    return result
}

private fun getWeatherByDays(response: String): List<WeatherModel> {
    if (response.isEmpty()) return listOf()
    val list = ArrayList<WeatherModel>()
    val obj = JSONObject(response)
    val city = obj.getJSONObject("location").getString("name")
    val days = obj.getJSONObject("forecast").getJSONArray("forecastday")
    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        list.add(
            WeatherModel(
                city = city,
                time = item.getString("date"),
                currentTemp = "",
                condition = item.getJSONObject("day").getJSONObject("condition").getString("text"),
                icon = item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                maxTemp = item.getJSONObject("day").getString("maxtemp_c"),
                minTemp = item.getJSONObject("day").getString("mintemp_c"),
                hours = item.getJSONArray("hour").toString()
            )
        )
    }
    list[0]= list[0].copy(
        time = obj.getJSONObject("current").getString("last_updated"),
        currentTemp = obj.getJSONObject("current").getString("temp_c"),
    )

    return list

}

fun getWeatherByHours(hours: String): List<WeatherModel> {
    if (hours.isEmpty()) return listOf()
    val hoursArray = JSONArray(hours)
    val list = ArrayList<WeatherModel>()
    for (i in 0 until hoursArray.length()) {
        val item = hoursArray[i] as JSONObject
        list.add(
            WeatherModel(
                city = "",
                time = item.getString("time"),
                currentTemp = item.getString("temp_c"),
                condition = item.getJSONObject("condition").getString("text"),
                icon = item.getJSONObject("condition").getString("icon"),
                maxTemp = "",
                minTemp = "",
                hours = ""
            )
        )
    }

    return list

}
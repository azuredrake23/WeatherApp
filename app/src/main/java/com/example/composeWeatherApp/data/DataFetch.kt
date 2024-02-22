package com.example.composeWeatherApp.data

import com.example.composeWeatherApp.data.models.WeatherModel
import org.json.JSONArray
import org.json.JSONObject

const val API_KEY = "d6425ef100db4ee68c3133507231812"

fun getWeatherByDays(response: String): List<WeatherModel> {
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
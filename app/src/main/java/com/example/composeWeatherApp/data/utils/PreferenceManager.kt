package com.example.composeWeatherApp.data.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.composeWeatherApp.utils.Constants.SEARCH_SIZE

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveList(key: String, newElement: String) {
        val savedList = getList(key)
        var serializedList = ""
        if (savedList.all { it.lowercase() != newElement.lowercase() }) {
            if (savedList.size <= SEARCH_SIZE) {
                serializedList = savedList.joinToString(",") + "," + newElement
                sharedPreferences.edit {
                    putString(key, serializedList)
                }
            } else {
                val newList = getList(key).toMutableList()
                newList.removeLast()
                newList.add(0, newElement)
                serializedList = newList.joinToString(",")
                sharedPreferences.edit {
                    putString(key, serializedList)
                }
            }
        }
    }

    private fun getList(key: String): List<String> {
        val serializedList = sharedPreferences.getString(key, "") ?: ""
        return serializedList.split(",")
    }
}
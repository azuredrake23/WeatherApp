package com.example.composeWeatherApp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchList")
data class SearchItemModel(

    @PrimaryKey
    @ColumnInfo(name = "searchItemName")
    var searchItemName: String

    )
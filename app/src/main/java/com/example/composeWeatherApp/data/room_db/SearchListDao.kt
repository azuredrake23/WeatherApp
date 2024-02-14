package com.example.composeWeatherApp.data.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchListDao {

    @Query("SELECT * FROM searchList")
    fun getSearchList(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchItem(searchItemModel: SearchItemModel)

    @Query ("DELETE FROM searchList WHERE searchItemName =:searchItem")
    suspend fun removeSearchItem(searchItem: String)
}
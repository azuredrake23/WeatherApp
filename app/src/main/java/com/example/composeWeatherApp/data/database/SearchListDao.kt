package com.example.composeWeatherApp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchListDao {

    @Query("SELECT * FROM searchList")
    fun getList(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchItem(searchItemModel: SearchItemModel)

    @Query ("DELETE FROM searchList WHERE searchItemName =:searchItem")
    suspend fun removeSearchItem(searchItem: String)
}
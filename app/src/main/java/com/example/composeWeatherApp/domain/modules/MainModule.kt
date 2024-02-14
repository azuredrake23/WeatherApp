package com.example.composeWeatherApp.domain.modules

import android.content.Context
import androidx.room.Room
import com.example.composeWeatherApp.data.repositories_Impl.SearchListRepImpl
import com.example.composeWeatherApp.data.room_db.MyDatabase
import com.example.composeWeatherApp.data.room_db.SearchListDao
import com.example.composeWeatherApp.domain.repositories.searchListRep.SearchListRep
import com.example.composeWeatherApp.utils.Constants.SEARCH_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
//    (includes = [ViewModelModule::class])
@InstallIn(SingletonComponent::class)
class MainModule {

    @Singleton
    @Provides
    fun provideSearchDatabase(@ApplicationContext context : Context): MyDatabase =
        Room.databaseBuilder(context, MyDatabase::class.java, SEARCH_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideSearchListDao(appDatabase: MyDatabase): SearchListDao {
        return appDatabase.searchListDao()
    }

    @Singleton
    @Provides
    fun provideSearchListRepImpl(searchListDao: SearchListDao): SearchListRep = SearchListRepImpl(searchListDao)
}
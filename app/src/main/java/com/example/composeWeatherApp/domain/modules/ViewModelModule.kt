package com.example.composeWeatherApp.domain.modules

import androidx.lifecycle.SavedStateHandle
import com.example.composeWeatherApp.domain.repositories.searchListRep.SearchListRep
import com.example.composeWeatherApp.domain.repositories.searchListRep.usecases.AddNewSearchItemUseCase
import com.example.composeWeatherApp.domain.repositories.searchListRep.usecases.GetSearchListUseCase
import com.example.composeWeatherApp.domain.repositories.searchListRep.usecases.RemoveSearchItemUseCase
import com.example.composeWeatherApp.ui.screens.search.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//class ViewModelModule {
//    @Singleton
//    @Provides
//    fun provideAddNewSearchItemUseCase(searchListRep: SearchListRep): AddNewSearchItemUseCase =
//        AddNewSearchItemUseCase(searchListRep)
//
//    @Singleton
//    @Provides
//    fun provideGetSearchListUseCase(searchListRep: SearchListRep): GetSearchListUseCase =
//        GetSearchListUseCase(searchListRep)
//
//    @Singleton
//    @Provides
//    fun provideRemoveSearchItemUseCase(searchListRep: SearchListRep): RemoveSearchItemUseCase =
//        RemoveSearchItemUseCase(searchListRep)
//
//    @Singleton
//    @Provides
//    fun provideSavedStateHandle(): SavedStateHandle = SavedStateHandle()
//
//    @Singleton
//    @Provides
//    fun provideSearchViewModel(
//        addNewSearchItemUseCase: AddNewSearchItemUseCase,
//        getSearchListUseCase: GetSearchListUseCase,
//        removeSearchItemUseCase: RemoveSearchItemUseCase,
//        savedStateHandle: SavedStateHandle
//    ): SearchViewModel = SearchViewModel(savedStateHandle, getSearchListUseCase, addNewSearchItemUseCase, removeSearchItemUseCase)
//}
package com.example.composeWeatherApp.domain.modules

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
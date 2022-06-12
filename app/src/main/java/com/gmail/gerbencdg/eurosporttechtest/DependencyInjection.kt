package com.gmail.gerbencdg.eurosporttechtest

import com.gmail.gerbencdg.eurosporttechtest.api.NewsFeedApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideNewsFeedApiService(): NewsFeedApiService {
        return NewsFeedApiService.create()
    }
}
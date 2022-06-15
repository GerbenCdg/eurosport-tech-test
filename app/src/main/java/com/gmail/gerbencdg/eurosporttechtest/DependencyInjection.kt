package com.gmail.gerbencdg.eurosporttechtest

import android.content.Context
import com.gmail.gerbencdg.eurosporttechtest.api.NewsFeedApiService
import com.gmail.gerbencdg.eurosporttechtest.data.source.INewsFeedRepository
import com.gmail.gerbencdg.eurosporttechtest.data.source.NewsFeedDataSource
import com.gmail.gerbencdg.eurosporttechtest.data.source.NewsFeedRepository
import com.gmail.gerbencdg.eurosporttechtest.data.source.local.NewsFeedDatabase
import com.gmail.gerbencdg.eurosporttechtest.data.source.local.NewsFeedLocalDataSource
import com.gmail.gerbencdg.eurosporttechtest.data.source.remote.NewsFeedRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NewsFeedDatabase {
        return NewsFeedDatabase.getInstance(context)
    }
}


@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideNewsFeedApiService(): NewsFeedApiService {
        return NewsFeedApiService.create()
    }
}


@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {

    @Singleton
    @Named("NewsFeedLocalDataSource")
    @Provides
    fun provideLocalDataSource(newsFeedDatabase: NewsFeedDatabase): NewsFeedDataSource {
        return NewsFeedLocalDataSource(newsFeedDatabase);
    }

    @Singleton
    @Named("NewsFeedRemoteDataSource")
    @Provides
    fun provideRemoteDataSource(apiService: NewsFeedApiService): NewsFeedDataSource {
        return NewsFeedRemoteDataSource(apiService);
    }

    @Singleton
    @Provides
    fun provideNewsFeedRepository(
        @Named("NewsFeedLocalDataSource") newsFeedLocal: NewsFeedDataSource,
        @Named("NewsFeedRemoteDataSource") newsFeedRemote: NewsFeedDataSource,
    ): INewsFeedRepository {
        return NewsFeedRepository(newsFeedLocal, newsFeedRemote)
    }
}


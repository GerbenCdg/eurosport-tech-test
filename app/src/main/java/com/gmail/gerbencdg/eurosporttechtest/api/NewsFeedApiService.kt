package com.gmail.gerbencdg.eurosporttechtest.api

import com.gmail.gerbencdg.eurosporttechtest.app.Constants.NEWSFEED_API_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET

/**
 * A retrofit service to fetch News Posts.
 */
interface NewsFeedApiService {

    @GET("edfefba")
    suspend fun getNewsFeed(): NewsFeedDto

    companion object {

        fun create(): NewsFeedApiService {

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(NEWSFEED_API_URL)
                .build()
                .create()
        }

    }
}

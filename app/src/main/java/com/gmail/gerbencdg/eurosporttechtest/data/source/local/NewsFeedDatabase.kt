package com.gmail.gerbencdg.eurosporttechtest.data.source.local

import android.content.Context
import androidx.room.*
import com.gmail.gerbencdg.eurosporttechtest.app.Constants


@Database(entities = [StoryPostDb::class, VideoPostDb::class], version = 1)
abstract class NewsFeedDatabase : RoomDatabase() {

    abstract val newsFeedDao : NewsFeedDao

    companion object {

        @Volatile
        private lateinit var instance: NewsFeedDatabase

        fun getInstance(context: Context): NewsFeedDatabase {
            synchronized(NewsFeedDatabase::class.java) {
                if (!Companion::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsFeedDatabase::class.java,
                        Constants.NEWSFEED_DATABASE_NAME
                    )
                        .build()
                }
            }
            return instance
        }
    }
}
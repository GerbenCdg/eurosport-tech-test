package com.gmail.gerbencdg.eurosporttechtest.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsFeedDao {

    @Query("select * from StoryPost " +
            "order by date DESC")
    fun observeStoryPosts() : LiveData<List<StoryPostDb>>

    @Query("select * from VideoPost " +
            "order by date DESC")
    fun observeVideoPosts() : LiveData<List<VideoPostDb>>

    @Query("select * from StoryPost " +
            "order by date DESC")
    fun getStoryPosts() : List<StoryPostDb>

    @Query("select * from VideoPost " +
            "order by date DESC")
    fun getVideoPosts() : List<VideoPostDb>


    @Insert
    suspend fun insertAll(vararg posts: StoryPostDb)

    @Insert
    suspend fun insertAll(vararg posts: VideoPostDb)


    @Query("delete from VideoPost")
    suspend fun deleteVideoPosts()

    @Query("delete from StoryPost")
    suspend fun deleteStoryPosts()
}

package com.chikemgbemena.warpspeed.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chikemgbemena.warpspeed.cache.models.MovieCacheEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getMovies(): List<MovieCacheEntity>

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :title || '%'")
    fun searchMovies(title: String): List<MovieCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieCacheEntity>)

    @Query("DELETE FROM movies")
    fun clearMovies(): Int
}
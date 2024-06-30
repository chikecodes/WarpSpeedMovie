package com.chikemgbemena.warpspeed.cache.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chikemgbemena.warpspeed.cache.utils.CacheConstants

@Entity(tableName = CacheConstants.MOVIE_TABLE_NAME)
data class MovieCacheEntity(
    @PrimaryKey
    @ColumnInfo(name = "imdb_id")
    val imdbId: String,
    val title: String,
    val year: String,
    val poster: String,
    val type: String
)

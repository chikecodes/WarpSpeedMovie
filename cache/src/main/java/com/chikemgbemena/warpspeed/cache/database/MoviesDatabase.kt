package com.chikemgbemena.warpspeed.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chikemgbemena.warpspeed.cache.dao.MovieDao
import com.chikemgbemena.warpspeed.cache.models.MovieCacheEntity
import com.chikemgbemena.warpspeed.cache.utils.CacheConstants
import javax.inject.Inject

@Database(
    entities = [MovieCacheEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedMovieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MoviesDatabase::class.java,
            CacheConstants.DB_NAME
        ).build()
    }
}

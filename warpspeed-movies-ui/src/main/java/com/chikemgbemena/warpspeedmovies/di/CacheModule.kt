package com.chikemgbemena.warpspeedmovies.di

import android.content.Context
import com.chikemgbemena.warpspeed.cache.MovieCacheImp
import com.chikemgbemena.warpspeed.cache.dao.MovieDao
import com.chikemgbemena.warpspeed.cache.database.MoviesDatabase
import com.chikemgbemena.warpspeed.cache.utils.CachePreferencesHelper
import com.chikemgbemena.warpspeed.data.repository.MovieCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): MoviesDatabase = MoviesDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideMovieDao(moviesDatabase: MoviesDatabase): MovieDao = moviesDatabase.cachedMovieDao()

    @Provides
    @Singleton
    fun provideMovieCache(movieCache: MovieCacheImp): MovieCache = movieCache

    @Provides
    @Singleton
    fun providePreferenceHelper(@ApplicationContext context: Context): CachePreferencesHelper =
        CachePreferencesHelper(context)
}

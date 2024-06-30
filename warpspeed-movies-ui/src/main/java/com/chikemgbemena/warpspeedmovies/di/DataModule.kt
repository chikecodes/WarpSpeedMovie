package com.chikemgbemena.warpspeedmovies.di

import com.chikemgbemena.warpspeed.data.MovieRepositoryImp
import com.chikemgbemena.warpspeed.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMovieRepository(movieRepository: MovieRepositoryImp): MovieRepository =
        movieRepository
}

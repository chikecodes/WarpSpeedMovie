package com.chikemgbemena.warpspeedmovies.di

import com.chikemgbemena.warpspeedmovies.BuildConfig
import com.chikemgbemena.warpspeed.data.repository.MovieRemote
import com.chikemgbemena.warpspeed.remote.api.MovieService
import com.chikemgbemena.warpspeed.remote.api.ServiceFactory
import com.chikemgbemena.warpspeed.remote.repository.MovieRemoteImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    @Named("omdbApiKey")
    fun provideApiKey(): String = BuildConfig.OMDB_API_KEY

    @Provides
    @Singleton
    fun provideMovieService(): MovieService {
        return ServiceFactory.create(BuildConfig.DEBUG, BuildConfig.BASE_URL)
    }

    @Provides
    @Singleton
    fun provideMovieRemote(movieRemote: MovieRemoteImp): MovieRemote = movieRemote
}

package amaterek.movie.app.di

import amaterek.movie.data.TmdbMovieRepositoryQualifier
import amaterek.movie.data.fake.FakeMovieRepository
import amaterek.movie.data.tmdb.TmdbConfig
import amaterek.movie.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        tmdbConfig: TmdbConfig,
        @TmdbMovieRepositoryQualifier tmdbMovieRepositoryProvider: Provider<MovieRepository>,
    ): MovieRepository =
        if (tmdbConfig.apiKey.isEmpty()) {
            FakeMovieRepository()
        } else {
            tmdbMovieRepositoryProvider.get()
        }
}
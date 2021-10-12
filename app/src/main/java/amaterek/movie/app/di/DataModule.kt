package amaterek.movie.app.di

import amaterek.movie.app.BuildConfig
import amaterek.movie.app.R
import amaterek.movie.data.fake.FakeMovieRepository
import amaterek.movie.data.tmdb.TmdbConfig
import amaterek.movie.domain.repository.MovieRepository
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideTmdbTmdbConfig(@ApplicationContext context: Context): TmdbConfig =
        TmdbConfig(
            baseUrl = BuildConfig.TMDB_BASE_URL,
            basePosterImageUrl = "${BuildConfig.TMDB_BASE_IMAGE_URL}${context.getString(R.string.tmdb_poster_image_size)}/",
            baseBackdropImageUrl = "${BuildConfig.TMDB_BASE_IMAGE_URL}${context.getString(R.string.tmdb_backdrop_image_size)}/",
            apiKey = BuildConfig.TMDB_API_KEY,
        )

    @Provides
    @Singleton
    fun provideMovieRepository(
        tmdbConfig: TmdbConfig,
        @Named("TMDB") tmdbMovieRepositoryProvider: Provider<MovieRepository>,
    ): MovieRepository =
        if (tmdbConfig.apiKey.isEmpty()) {
            FakeMovieRepository()
        } else {
            tmdbMovieRepositoryProvider.get()
        }
}
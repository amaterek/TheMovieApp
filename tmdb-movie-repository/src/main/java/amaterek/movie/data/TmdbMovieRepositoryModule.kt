package amaterek.movie.data

import amaterek.movie.data.local.MoviesDatabase
import amaterek.movie.data.tmdb.TmdbConfig
import amaterek.movie.domain.repository.MovieRepository
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TmdbMovieRepositoryQualifier

private const val MOVIES_DB = "movies.db"

@Module
@InstallIn(SingletonComponent::class)
internal class MovieDatabaseModule {

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
    fun provideMovieDatabase(@ApplicationContext context: Context): MoviesDatabase =
        Room.databaseBuilder(context, MoviesDatabase::class.java, MOVIES_DB).build()

    @Provides
    @Singleton
    @TmdbMovieRepositoryQualifier
    fun provideMovieRepository(tmdbMovieRepository: TmdbMovieRepository): MovieRepository =
        tmdbMovieRepository
}
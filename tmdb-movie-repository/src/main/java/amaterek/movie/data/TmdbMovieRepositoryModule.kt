package amaterek.movie.data

import amaterek.movie.data.local.MoviesDatabase
import amaterek.movie.domain.repository.MovieRepository
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

private const val MOVIES_DB = "movies.db"

@Module
@InstallIn(SingletonComponent::class)
internal class MovieDatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MoviesDatabase =
        Room.databaseBuilder(context, MoviesDatabase::class.java, MOVIES_DB).build()

    @Provides
    @Named("TMDB")
    fun provideMovieRepository(tmdbMovieRepository: TmdbMovieRepository): MovieRepository = tmdbMovieRepository
}
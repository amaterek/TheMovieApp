package amaterek.movie.app.di

import amaterek.movie.data.fake.FakeMovieRepository
import amaterek.movie.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRepository = FakeMovieRepository()
}
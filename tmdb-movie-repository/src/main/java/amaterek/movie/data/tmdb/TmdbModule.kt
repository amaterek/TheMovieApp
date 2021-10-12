package amaterek.movie.data.tmdb

import amaterek.movie.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class TmdbModule {

    @Provides
    @Reusable
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
        }.build()

    @Provides
    @Reusable
    fun provideTmdbApiService(httpClient: OkHttpClient, tmdbConfig: TmdbConfig): TmdbApiService =
        Retrofit.Builder()
            .baseUrl(tmdbConfig.baseUrl)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
}
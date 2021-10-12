package amaterek.movie.data.tmdb

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("movie/{type}")
    suspend fun getMovieList(
        @Path("type") type: String,
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("with_genres", encoded = true) genres: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): TmdbMovieListPage

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): TmdbMovieDetails

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") phrase: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): TmdbMovieListPage
}

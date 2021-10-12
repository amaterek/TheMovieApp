package amaterek.movie.data.tmdb

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbMovie(
    var id: Long,
    val title: String,
    val vote_average: Float?,
    val release_date: String? = null,
    val poster_path: String? = null,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>? = null,
)

@JsonClass(generateAdapter = true)
data class TmdbMovieDetails(
    var id: Long,
    val title: String,
    val vote_average: Float?,
    val release_date: String? = null,
    val poster_path: String? = null,
    val backdrop_path: String? = null,
    val genres: List<TmdbMovieDetailsGenre>? = null,
    val overview: String? = null,
    val budget: Long? = null,
)

@JsonClass(generateAdapter = true)
data class TmdbMovieDetailsGenre(
    val id: Int,
    val name: String
)

@JsonClass(generateAdapter = true)
data class TmdbMovieListPage(
    val page: Int,
    val results: List<TmdbMovie>,
    val total_pages: Int
)

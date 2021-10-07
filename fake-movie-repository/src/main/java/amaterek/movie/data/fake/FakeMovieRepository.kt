package amaterek.movie.data.fake

import amaterek.movie.domain.common.FailureCause
import amaterek.movie.domain.common.QueryResult
import amaterek.movie.domain.model.*
import amaterek.movie.domain.model.MovieQuery.Type
import amaterek.movie.domain.repository.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*

class FakeMovieRepository : MovieRepository {

    private val _favoriteMoviesIds = MutableStateFlow(emptySet<Long>())
    override val favoriteMoviesIds: StateFlow<Set<Long>>
        get() = _favoriteMoviesIds

    override suspend fun getMovieListPage(query: MovieQuery, page: Int): QueryResult<MovieList> {
        delay(1000)
        return getMoviesBySearchType(query.type)
            .filterByGenres(query.genres)
            .pagedResult(page)
    }

    internal fun getMoviesBySearchType(type: Type) =
        when (type) {
            is Type.ByCategory -> getMoviesByCategory(type.category)
            is Type.ByPhrase -> getMoviesByPhrase(type.phrase)
        }

    internal fun getMoviesByCategory(category: MovieCategory) =
        when (category) {
            MovieCategory.ALL -> movies
            MovieCategory.NOW_PLAYING -> movies.subList(2, 20)
            MovieCategory.POPULAR -> movies.subList(8, 12)
            MovieCategory.TOP_RATED -> movies.subList(10, 15)
            MovieCategory.UPCOMING -> movies.subList(15, 20)
            MovieCategory.FAVORITE -> movies.filter { it.isFavorite }
        }

    internal fun getMoviesByPhrase(phrase: String) =
        movies.filter { it.title.contains(phrase, ignoreCase = true) }

    internal fun List<Movie>.filterByGenres(genres: Set<MovieGenre>) =
        if (genres.isNotEmpty()) filter { it.genres.containsAll(genres) } else this

    internal fun List<Movie>.pagedResult(page: Int): QueryResult<MovieList> {
        val pages = if (isEmpty()) 0 else (size - 1) / moviesPerPage + 1
        val minPage = if (isEmpty()) 0 else 1
        return if (page in minPage..pages) {
            QueryResult.Success(
                MovieList(
                    items = subList(
                        maxOf((page - 1) * moviesPerPage, 0),
                        minOf(page * moviesPerPage, size)
                    ).map {
                        if (favoriteMoviesIds.value.contains(it.id)) {
                            it.copy(isFavorite = true)
                        } else it
                    },
                    loadedPages = page,
                    totalPages = pages,
                )
            )
        } else QueryResult.Failure(cause = FailureCause.Error)
    }

    override suspend fun getMovieDetails(movieId: Long): QueryResult<MovieDetails> {
        delay(1000)
        val movie = movies.firstOrNull { it.id == movieId }
            ?: return QueryResult.Failure(cause = FailureCause.Error)
        return QueryResult.Success(
            MovieDetails(
                movie = if (favoriteMoviesIds.value.contains(movie.id)) {
                    movie.copy(isFavorite = true)
                } else movie,
                description = "The description of movie $movieId",
                budget = movieId * 1_000_000
            )
        )
    }

    override suspend fun setFavorite(movieId: Long, favorite: Boolean) {
        _favoriteMoviesIds.value = when (favorite) {
            true -> favoriteMoviesIds.value + movieId
            false -> favoriteMoviesIds.value - movieId
        }
    }

    companion object {
        const val moviesPerPage = 8

        @Suppress("SimpleDateFormat")
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        private fun date(value: String): Date? =
            try {
                dateFormat.parse(value)
            } catch (e: Exception) {
                null
            }

        val movies = listOf(
            Movie(
                id = 1L,
                title = "Movie 1",
                rating = MovieRating(10),
                genres = listOf(MovieGenre.ACTION, MovieGenre.SCIENCE_FICTION),
                releaseDate = date("2021-09-30"),
                posterUrl = "https://image.tmdb.org/t/p/w342/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/t9nyF3r0WAlJ7Kr6xcRYI4jr9jm.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 2L,
                title = "Movie 2",
                rating = MovieRating(15),
                genres = listOf(MovieGenre.ADVENTURE),
                releaseDate = date("2021-08-1"),
                posterUrl = "https://image.tmdb.org/t/p/w342/xmbU4JTUm8rsdtn7Y3Fcm30GpeT.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/8Y43POKjjKDGI9MH89NW0NAzzp8.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 3L,
                title = "Movie 3",
                rating = MovieRating(20),
                genres = listOf(MovieGenre.ANIMATION),
                releaseDate = date("2021-07-22"),
                posterUrl = "https://image.tmdb.org/t/p/w342/uIXF0sQGXOxQhbaEaKOi2VYlIL0.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/aO9Nnv9GdwiPdkNO79TISlQ5bbG.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 4L,
                title = "Movie 4",
                rating = MovieRating(25),
                genres = listOf(MovieGenre.COMEDY, MovieGenre.ACTION),
                releaseDate = date("2021-10-01"),
                posterUrl = "https://image.tmdb.org/t/p/w342/xYLBgw7dHyEqmcrSk2Sq3asuSq5.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/kTOheVmqSBDIRGrQLv2SiSc89os.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 5L,
                title = "Movie 5",
                rating = MovieRating(30),
                genres = listOf(MovieGenre.CRIME),
                releaseDate = date("2021-09-01"),
                posterUrl = "https://image.tmdb.org/t/p/w342/1BIoJGKbXjdFDAqUEiA2VHqkK1Z.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/cinER0ESG0eJ49kXlExM0MEWGxW.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 6L,
                title = "Movie 6",
                rating = MovieRating(35),
                genres = listOf(MovieGenre.DOCUMENTARY),
                releaseDate = date("2021-09-24"),
                posterUrl = "https://image.tmdb.org/t/p/w342/ApwmbrMlsuOay5rXQA4Kbz7qJAl.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/iDLtDgxLiYsarfdQ4msUhUqoNPp.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 7L,
                title = "Movie 7",
                rating = MovieRating(40),
                genres = listOf(MovieGenre.DRAMA),
                releaseDate = date("2021-07-28"),
                posterUrl = "https://image.tmdb.org/t/p/w342/9dKCd55IuTT5QRs989m9Qlb7d2B.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/7WJjFviFBffEJvkAms4uWwbcVUk.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 8L,
                title = "Movie 8",
                rating = MovieRating(45),
                genres = listOf(MovieGenre.FAMILY),
                releaseDate = date("2021-05-19"),
                posterUrl = "https://image.tmdb.org/t/p/w342/bOFaAXmWWXC3Rbv4u4uM9ZSzRXP.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/aT0XL7YLDx9GfpU2q8kgWUtn0on.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 9L,
                title = "Movie 9",
                rating = MovieRating(50),
                genres = listOf(MovieGenre.FANTASY),
                releaseDate = date("2021-07-01"),
                posterUrl = "https://image.tmdb.org/t/p/w342/uWStkK8bq9ixY3fc7y209ZleCoF.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/akwg1s7hV5ljeSYFfkw7hTHjVqk.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 10L,
                title = "Movie 10",
                rating = MovieRating(55),
                genres = listOf(MovieGenre.HISTORY),
                releaseDate = date("2021-02-09"),
                posterUrl = "https://image.tmdb.org/t/p/w342/l8HyObVj8fPrzacAPtGWWLDhcfh.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/3GgkzCDq6KYpcmJmcOKh27hYRyj.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 11L,
                title = "Movie 11",
                rating = MovieRating(60),
                genres = listOf(MovieGenre.HORROR),
                releaseDate = date("2021-08-09"),
                posterUrl = "https://image.tmdb.org/t/p/w342/ic0intvXZSfBlYPIvWXpU1ivUCO.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/mtRW6eAwOO27h3zrfU2foQFW7Hg.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 12L,
                title = "Movie 12",
                rating = MovieRating(65),
                genres = listOf(MovieGenre.MUSIC),
                releaseDate = date("2021-09-10"),
                posterUrl = "https://image.tmdb.org/t/p/w342/7PoomidF9HlMKXcAyOJ87lGkhSp.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/MDYanFolbT76dj0gsCbhS2GM5A.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 13L,
                title = "Movie 13",
                rating = MovieRating(70),
                genres = listOf(MovieGenre.MYSTERY),
                releaseDate = date("2021-09-23"),
                posterUrl = "https://image.tmdb.org/t/p/w342/hzq5XRGgm6NDMOW1idUvbpGqEkv.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/ugukqzx4gSzBd1yzmbWEHLkpGaS.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 14L,
                title = "Movie 14",
                rating = MovieRating(75),
                genres = listOf(MovieGenre.ROMANCE),
                releaseDate = date("2021-07-08"),
                posterUrl = "https://image.tmdb.org/t/p/w342/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/8s4h9friP6Ci3adRGahHARVd76E.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 15L,
                title = "Movie 15",
                rating = MovieRating(80),
                genres = listOf(MovieGenre.SCIENCE_FICTION),
                releaseDate = Date(15L),
                posterUrl = "https://image.tmdb.org/t/p/w342/uJgdT1boTSP0dDIjdTgGleg71l4.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/byflnwPMumyvrCW9SfO5Miq3647.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 16L,
                title = "Movie 16",
                rating = MovieRating(85),
                genres = listOf(MovieGenre.TV_MOVIE),
                releaseDate = date("2021-09-10"),
                posterUrl = "https://image.tmdb.org/t/p/w342/4YJNp1cquIkX8JxFwkKNEFQ9tgr.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/4YJNp1cquIkX8JxFwkKNEFQ9tgr.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 17L,
                title = "Movie 17",
                rating = MovieRating(90),
                genres = listOf(MovieGenre.THRILLER),
                releaseDate = date("2021-08-12"),
                posterUrl = "https://image.tmdb.org/t/p/w342/hRMfgGFRAZIlvwVWy8DYJdLTpvN.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/pUc51UUQb1lMLVVkDCaZVsCo37U.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 18L,
                title = "Movie 18",
                rating = MovieRating(91),
                genres = listOf(MovieGenre.WAR),
                releaseDate = Date(18L),
                posterUrl = "https://image.tmdb.org/t/p/w342/iUgygt3fscRoKWCV1d0C7FbM9TP.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w780/u5Fp9GBy9W8fqkuGfLBuuoJf57Z.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 19L,
                title = "Movie 19",
                rating = MovieRating(92),
                genres = listOf(MovieGenre.WESTERN),
                releaseDate = date("2021-08-26"),
                posterUrl = "https://image.tmdb.org/t/p/w342/bZnOioDq1ldaxKfUoj3DenHU7mp.jpg",
                backdropUrl = "",
                isFavorite = false,
            ),
            Movie(
                id = 20L,
                title = "Movie 20",
                rating = MovieRating(93),
                genres = listOf(MovieGenre.KIDS),
                releaseDate = null,
                posterUrl = "",
                backdropUrl = "",
                isFavorite = false,
            ),
        )
    }
}
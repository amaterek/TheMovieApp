package amaterek.movie.data.tmdb.mapper

import amaterek.movie.data.tmdb.TmdbMovie
import amaterek.movie.data.tmdb.TmdbMovieDetails
import amaterek.movie.data.tmdb.TmdbMovieDetailsGenre
import amaterek.movie.data.tmdb.TmdbMovieListPage
import amaterek.movie.domain.model.*
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class TmdbMovieMappersTest {

    @Test
    fun `maps TmdbMovie to Movie`() {
        assertEquals(
            testMovie1,
            testTmdbMovie1.toDomain(
                basePosterImagePath = basePosterImagePath,
                baseBackdropImagePath = baseBackdropImagePath,
                isFavorite = true
            )
        )
        assertEquals(
            testMovie2,
            testTmdbMovie2.toDomain(
                basePosterImagePath = basePosterImagePath,
                baseBackdropImagePath = baseBackdropImagePath,
                isFavorite = false
            )
        )
        assertEquals(
            testMovie3,
            testTmdbMovie3.toDomain(
                basePosterImagePath = basePosterImagePath,
                baseBackdropImagePath = baseBackdropImagePath,
                isFavorite = false
            )
        )
    }

    @Test
    fun `maps TmdbMovieListPage to MovieList`() {
        val favoriteMoviesIds = mockk<Set<Long>>()

        every { favoriteMoviesIds.contains(any()) } returns false
        every { favoriteMoviesIds.contains(testMovie1.id) } returns true

        assertEquals(
            testMovieList,
            testTmdbMovieList.toDomain(
                basePosterImagePath = basePosterImagePath,
                baseBackdropImagePath = baseBackdropImagePath,
                favoriteMoviesIds = favoriteMoviesIds
            )
        )
    }

    @Test
    fun `maps TmdbMovieDetails to MovieDetails`() {
        assertEquals(
            testMovieDetails1,
            testTmdbMovieDetails1.toDomain(
                basePosterImagePath = basePosterImagePath,
                baseBackdropImagePath = baseBackdropImagePath,
                isFavorite = true
            )
        )
        assertEquals(
            testMovieDetails2,
            testTmdbMovieDetails2.toDomain(
                basePosterImagePath = basePosterImagePath,
                baseBackdropImagePath = baseBackdropImagePath,
                isFavorite = false
            )
        )
    }

    private val basePosterImagePath = "basePosterUrl/"
    private val baseBackdropImagePath = "baseBackdropUrl/"

    private val testTmdbMovie1 = TmdbMovie(
        id = 101L,
        title = "Movie 1",
        vote_average = null,
        release_date = "2021-10-01",
        poster_path = "posterUrl1",
        backdrop_path = "backdropUrl1",
        genre_ids = listOf(878, 28)
    )

    private val testMovie1 = Movie(
        id = 101L,
        title = "Movie 1",
        rating = MovieRating(0),
        genres = listOf(MovieGenre.SCIENCE_FICTION, MovieGenre.ACTION),
        releaseDate = @Suppress("Deprecation") Date(2021 - 1900, 9, 1),
        posterUrl = "${basePosterImagePath}posterUrl1",
        backdropUrl = "${baseBackdropImagePath}backdropUrl1",
        isFavorite = true,
    )

    private val testTmdbMovie2 = TmdbMovie(
        id = 202L,
        title = "Movie 2",
        vote_average = 6.6f,
        release_date = null,
        poster_path = "posterUrl2",
        backdrop_path = null,
        genre_ids = listOf(14, 27)
    )

    private val testMovie2 = Movie(
        id = 202L,
        title = "Movie 2",
        rating = MovieRating(66),
        genres = listOf(MovieGenre.FANTASY, MovieGenre.HORROR),
        releaseDate = null,
        posterUrl = "${basePosterImagePath}posterUrl2",
        backdropUrl = "",
        isFavorite = false,
    )

    private val testTmdbMovie3 = TmdbMovie(
        id = 303L,
        title = "Movie 3",
        vote_average = 10f,
        release_date = null,
        poster_path = null,
        backdrop_path = "backdropUrl3",
        genre_ids = null
    )

    private val testMovie3 = Movie(
        id = 303L,
        title = "Movie 3",
        rating = MovieRating(100),
        genres = listOf(),
        releaseDate = null,
        posterUrl = "",
        backdropUrl = "${baseBackdropImagePath}backdropUrl3",
        isFavorite = false,
    )

    private val testTmdbMovieList = TmdbMovieListPage(
        results = listOf(testTmdbMovie1, testTmdbMovie2, testTmdbMovie3),
        page = 3,
        total_pages = 5,
    )

    private val testMovieList = MovieList(
        items = listOf(testMovie1, testMovie2, testMovie3),
        loadedPages = 3,
        totalPages = 5,
    )

    private val testTmdbMovieDetails1 = TmdbMovieDetails(
        id = 101L,
        title = "Movie 1",
        vote_average = null,
        release_date = "2021-10-01",
        poster_path = "posterUrl1",
        backdrop_path = "backdropUrl1",
        genres = listOf(
            TmdbMovieDetailsGenre(878, "Science-Fiction"),
            TmdbMovieDetailsGenre(28, "Action")
        ),
        overview = "Movie 1 decryption",
        budget = 1_300_010L,
    )

    private val testMovieDetails1 = MovieDetails(
        movie = testMovie1,
        description = "Movie 1 decryption",
        budget = 1_300_010L
    )

    private val testTmdbMovieDetails2 = TmdbMovieDetails(
        id = 202L,
        title = "Movie 2",
        vote_average = 6.6f,
        release_date = null,
        poster_path = "posterUrl2",
        backdrop_path = null,
        genres = listOf(
            TmdbMovieDetailsGenre(14, "Fantasy"),
            TmdbMovieDetailsGenre(27, "Horror")
        ),
        overview = "Movie 2 decryption",
        budget = null,
    )

    private val testMovieDetails2 = MovieDetails(
        movie = testMovie2,
        description = "Movie 2 decryption",
        budget = -1L
    )
}
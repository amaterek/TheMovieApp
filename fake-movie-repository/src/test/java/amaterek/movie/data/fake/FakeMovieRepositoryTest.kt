package amaterek.movie.data.fake

import amaterek.base.test.CoroutineTest
import amaterek.movie.domain.common.FailureCause
import amaterek.movie.domain.common.QueryResult
import amaterek.movie.data.fake.FakeMovieRepository.Companion.movies
import amaterek.movie.data.fake.FakeMovieRepository.Companion.moviesPerPage
import amaterek.movie.domain.model.*
import amaterek.movie.domain.model.MovieQuery.Type
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FakeMovieRepositoryTest : CoroutineTest() {

    private lateinit var subject: FakeMovieRepository

    @Before
    fun setUp() {
        subject = spyk(FakeMovieRepository())
    }

    @Test
    fun `WHEN getMovieList is called TTHEN returns filtered movie result`() = runBlockingTest {
        val query = mockk<MovieQuery>()
        val type = mockk<Type>()
        val moviesByType = mockk<List<Movie>>()
        val genres = mockk<Set<MovieGenre>>()
        val moviesByGenre = mockk<List<Movie>>()
        val queryResult = mockk<QueryResult<MovieList>>()
        val page = 1234567
        every { query.type } returns type
        every { query.genres } returns genres
        with(subject) {
            every { getMoviesBySearchType(type) } returns moviesByType
            every { moviesByType.filterByGenres(genres) } returns moviesByGenre
            every { moviesByGenre.pagedResult(page) } returns queryResult
        }

        assertEquals(queryResult, subject.getMovieListPage(query, page))
    }

    @Test
    fun `WHEN getMoviesBySearchType is called with type ByCategory TTHEN returns result of getMoviesByCategory`() {
        val byCategoryMovies = mockk<List<Movie>>()
        val byCategory = mockk<Type.ByCategory>()
        val category = mockk<MovieCategory>()
        every { byCategory.category } returns category
        every { subject.getMoviesByCategory(category) } returns byCategoryMovies

        assertEquals(byCategoryMovies, subject.getMoviesBySearchType(byCategory))
    }

    @Test
    fun `WHEN getMoviesBySearchType is called with type ByPhrase THEN returns result of getMoviesByPhrase`() {
        val byPhraseMovies = mockk<List<Movie>>()
        val byPhrase = mockk<Type.ByPhrase>()
        val phrase = "query phrase"
        every { byPhrase.phrase } returns phrase
        every { subject.getMoviesByPhrase(phrase) } returns byPhraseMovies

        assertEquals(byPhraseMovies, subject.getMoviesBySearchType(byPhrase))
    }

    @Test
    fun `WHEN getMoviesByCategory is called with category ALL THEN returns all movies`() {
        assertEquals(movies, subject.getMoviesByCategory(MovieCategory.ALL))
    }

    @Test
    fun `WHEN getMoviesByCategory is called with category FAVORITE THEN returns favourite movies`() {
        assertEquals(
            movies.filter { it.isFavorite },
            subject.getMoviesByCategory(MovieCategory.FAVORITE)
        )
    }

    @Test
    fun `WHEN getMoviesByPhrase is called with phrase '' THEN returns filtered movies`() {
        assertEquals(movies, subject.getMoviesByPhrase(""))
    }

    @Test
    fun `WHEN filterByGenres is called with MovieGenreAll THEN returns all movies`() {
        with(subject) {
            assertEquals(movies, movies.filterByGenres(emptySet()))
        }
    }

    @Test
    fun `WHEN filterByGenres is called with specific genre THEN returns filtered movies`() {
        with(subject) {
            enumValues<MovieGenre>().forEach { genre ->
                assertEquals(
                    movies.filter { it.genres.contains(genre) },
                    movies.filterByGenres(setOf(genre))
                )
            }
        }
    }

    @Test
    fun `WHEN pagedResult is called with page 0 on non empty THEN returns Failure with Error cause result`() {
        with(subject) {
            assertEquals(
                QueryResult.Failure(FailureCause.Error),
                movies.pagedResult(0)
            )
        }
    }

    @Test
    fun `WHEN pagedResult is called with page 0 on empty movies THEN returns Success result`() {
        with(subject) {
            assertEquals(
                QueryResult.Success(
                    MovieList(
                        items = emptyList(),
                        loadedPages = 0,
                        totalPages = 0,
                    )
                ),
                emptyList<Movie>().pagedResult(0)
            )
        }
    }

    @Test
    fun `WHEN pagedResult is called with page 1 on non empty movies THEN returns Success result`() {
        with(subject) {
            assertEquals(
                QueryResult.Success(
                    MovieList(
                        items = movies.subList(0, moviesPerPage),
                        loadedPages = 1,
                        totalPages = pages(movies.size),
                    )
                ),
                movies.pagedResult(1)
            )
        }
    }

    @Test
    fun `WHEN pagedResult is called with page 2 on non empty movies THEN returns Success result`() {
        with(subject) {
            assertEquals(
                QueryResult.Success(
                    MovieList(
                        items = movies.subList(moviesPerPage, moviesPerPage * 2),
                        loadedPages = 2,
                        totalPages = pages(movies.size),
                    )
                ),
                movies.pagedResult(2)
            )
        }
    }

    @Test
    fun `WHEN pagedResult is called with page 1 on 1 elements list THEN returns Success result`() {
        val testMovies = movies.subList(0, 1)
        with(subject) {
            assertEquals(
                QueryResult.Success(
                    MovieList(
                        items = testMovies.subList(0, 1),
                        loadedPages = 1,
                        totalPages = 1,
                    )
                ),
                testMovies.pagedResult(1)
            )
        }
    }

    @Test
    fun `WHEN pagedResult is called with page 1 on moviesPerPage elements list THEN returns Success result`() {
        val testMovies = movies.subList(0, moviesPerPage)
        with(subject) {
            assertEquals(
                QueryResult.Success(
                    MovieList(
                        items = testMovies.subList(0, moviesPerPage),
                        loadedPages = 1,
                        totalPages = 1,
                    )
                ),
                testMovies.pagedResult(1)
            )
        }
    }

    @Test
    fun `WHEN pagedResult is called with page 2 on moviesPerPage + 1 elements list THEN returns Success result`() {
        val testMovies = movies.subList(0, moviesPerPage + 1)
        with(subject) {
            assertEquals(
                QueryResult.Success(
                    MovieList(
                        items = testMovies.subList(moviesPerPage, moviesPerPage + 1),
                        loadedPages = 2,
                        totalPages = 2,
                    )
                ),
                testMovies.pagedResult(2)
            )
        }
    }

    @Test
    fun `WHEN pagedResult is called with page 1 on empty list THEN returns Failure result`() {
        val testMovies = emptyList<Movie>()
        with(subject) {
            assertEquals(
                QueryResult.Failure(cause = FailureCause.Error),
                testMovies.pagedResult(1)
            )
        }
    }

    @Test
    fun `WHEN pagedResult is called with total page + 1 on non empty list THEN returns Failure result`() {
        val testMovies = movies
        with(subject) {
            assertEquals(
                QueryResult.Failure(cause = FailureCause.Error),
                testMovies.pagedResult(pages(testMovies.size) + 1)
            )
        }
    }

    private fun pages(itemsCount: Int) = (itemsCount - 1) / moviesPerPage + 1
}
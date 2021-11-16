package amaterek.movie.base.moviesloader

import amaterek.base.test.CoroutineTest
import amaterek.base.test.verify
import amaterek.movie.base.LoadingState
import amaterek.movie.domain.common.FailureCause
import amaterek.movie.domain.common.QueryResult
import amaterek.movie.domain.model.*
import amaterek.movie.domain.usecase.GetMoviesPageUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class MoviesLoaderTest : CoroutineTest() {

    private lateinit var subject: MoviesLoader

    @MockK
    private lateinit var movieQuery: MovieQuery

    @MockK
    private lateinit var getMoviesPageUseCase: GetMoviesPageUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        subject = MoviesLoader.create(
            query = movieQuery,
            getMoviesPageUseCase = getMoviesPageUseCase,
        )
    }

    @Test
    fun `WHEN use case's response is successful for all pages THEN calling loadMore triggers emitting proper MoviesLoaderState`() =
        runBlockingTest {
            val moviesPage1 = MovieList(
                items = listOf(mockk(), mockk()),
                loadedPages = 1,
                totalPages = 2,
            )
            val moviesPage2 = MovieList(
                items = listOf(mockk(), mockk()),
                loadedPages = 2,
                totalPages = 2,
            )
            coEvery { getMoviesPageUseCase(movieQuery, page = 1) } returns
                    QueryResult.Success(moviesPage1)
            coEvery { getMoviesPageUseCase(movieQuery, page = 2) } returns
                    QueryResult.Success(moviesPage2)

            subject.stateFlow.verify(this) {
                verifyItem(
                    LoadingState.Idle(
                        MoviesLoaderState(
                            movies = emptyList(),
                            loadedPages = 0,
                            totalPages = -1,
                        )
                    )
                )
                verifyItem(
                    LoadingState.Loading(
                        MoviesLoaderState(
                            movies = emptyList(),
                            loadedPages = 0,
                            totalPages = -1,
                        )
                    )
                )
                verifyItem(
                    LoadingState.Idle(
                        MoviesLoaderState(
                            movies = moviesPage1.items,
                            loadedPages = 1,
                            totalPages = 2,
                        )
                    )
                )
                verifyItem(
                    LoadingState.Loading(
                        MoviesLoaderState(
                            movies = moviesPage1.items,
                            loadedPages = 1,
                            totalPages = 2,
                        )
                    )
                )
                verifyItem(
                    LoadingState.Idle(
                        MoviesLoaderState(
                            movies = moviesPage1.items + moviesPage2.items,
                            loadedPages = 2,
                            totalPages = 2,
                        )
                    )
                )
            }

            subject.loadMore()
            subject.loadMore()
            subject.loadMore()
        }

    @Test
    fun `WHEN use case response is failure for an pages THEN calling loadMore triggers emitting proper MoviesLoaderState`() =
        runBlockingTest {
            val moviesPage1 = MovieList(
                items = listOf(mockk(), mockk()),
                loadedPages = 1,
                totalPages = 2,
            )
            val testFailureCase = mockk<FailureCause>()
            coEvery { getMoviesPageUseCase(movieQuery, page = 1) } returns
                    QueryResult.Success(moviesPage1)
            coEvery { getMoviesPageUseCase(movieQuery, page = 2) } returns
                    QueryResult.Failure(testFailureCase)

            subject.stateFlow.verify(this) {
                verifyItem(
                    LoadingState.Idle(
                        MoviesLoaderState(
                            movies = emptyList(),
                            loadedPages = 0,
                            totalPages = -1,
                        )
                    )
                )
                verifyItem(
                    LoadingState.Loading(
                        MoviesLoaderState(
                            movies = emptyList(),
                            loadedPages = 0,
                            totalPages = -1,
                        )
                    )
                )
                verifyItem(
                    LoadingState.Idle(
                        MoviesLoaderState(
                            movies = moviesPage1.items,
                            loadedPages = 1,
                            totalPages = 2,
                        )
                    )
                )
                verifyItem(
                    LoadingState.Loading(
                        MoviesLoaderState(
                            movies = moviesPage1.items,
                            loadedPages = 1,
                            totalPages = 2,
                        )
                    )
                )
                verifyItem(
                    LoadingState.Failure(
                        MoviesLoaderState(
                            movies = moviesPage1.items,
                            loadedPages = 1,
                            totalPages = 2,
                        ),
                        testFailureCase
                    )
                )
            }

            subject.loadMore()
            subject.loadMore()
        }

    @Test
    fun `WHEN setFavoriteMoviesIds is called THEN then updates Movie's isFavorite and emits proper MoviesLoaderState`() =
        runBlockingTest {
            val testMovies = listOf(
                Movie(
                    id = 1L,
                    title = "Movie 1",
                    rating = MovieRating(10),
                    genres = listOf(MovieGenre.ACTION, MovieGenre.SCIENCE_FICTION),
                    releaseDate = null,
                    posterUrl = "posterUrl 1",
                    backdropUrl = "backdropUrl 1",
                    isFavorite = false,
                ),
                Movie(
                    id = 2L,
                    title = "Movie 2",
                    rating = MovieRating(20),
                    genres = listOf(MovieGenre.ADVENTURE, MovieGenre.HISTORY),
                    releaseDate = null,
                    posterUrl = "posterUrl 2",
                    backdropUrl = "backdropUrl 2",
                    isFavorite = true,
                ),
                Movie(
                    id = 3L,
                    title = "Movie 3",
                    rating = MovieRating(30),
                    genres = listOf(MovieGenre.COMEDY, MovieGenre.FAMILY),
                    releaseDate = null,
                    posterUrl = "posterUrl 3",
                    backdropUrl = "backdropUrl 3",
                    isFavorite = false,
                ),
            )
            val moviesPage1 = MovieList(
                items = testMovies,
                loadedPages = 1,
                totalPages = 2
            )
            coEvery { getMoviesPageUseCase(movieQuery, page = 1) } returns
                    QueryResult.Success(moviesPage1)

            subject.stateFlow.verify(this) {
                verifyItem(
                    LoadingState.Idle(
                        MoviesLoaderState(
                            movies = emptyList(),
                            loadedPages = 0,
                            totalPages = -1,
                        )
                    )
                )
                verifyItem(
                    LoadingState.Loading(
                        MoviesLoaderState(
                            movies = emptyList(),
                            loadedPages = 0,
                            totalPages = -1,
                        )
                    )
                )
                verifyItem(
                    LoadingState.Idle(
                        MoviesLoaderState(
                            movies = moviesPage1.items,
                            loadedPages = 1,
                            totalPages = 2,
                        )
                    )
                )
                verifyItem(
                    LoadingState.Idle(
                        MoviesLoaderState(
                            movies = listOf(
                                testMovies[0].copy(isFavorite = true),
                                testMovies[1].copy(isFavorite = false),
                                testMovies[2]
                            ),
                            loadedPages = 1,
                            totalPages = 2,
                        )
                    )
                )
            }

            subject.loadMore()
            subject.setFavoriteMoviesIds(setOf(1))
        }
}
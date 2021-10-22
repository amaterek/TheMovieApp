package amaterek.movie.app.ui.movielist

import amaterek.base.test.android.ViewModelTest
import amaterek.base.test.coVerifyCalledOnes
import amaterek.base.test.verify
import amaterek.movie.base.LoadingState
import amaterek.movie.base.moviesloader.MoviesLoader
import amaterek.movie.base.moviesloader.MoviesState
import amaterek.movie.domain.model.Movie
import amaterek.movie.domain.model.MovieCategory
import amaterek.movie.domain.model.MovieQuery
import amaterek.movie.domain.usecase.GetMoviesPageUseCase
import amaterek.movie.domain.usecase.ObserveFavoriteMoviesUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class MovieListViewModelTest : ViewModelTest() {

    @MockK
    private lateinit var getMoviesPageUseCase: GetMoviesPageUseCase

    @MockK
    private lateinit var observeFavoriteMoviesUseCase: ObserveFavoriteMoviesUseCase

    @MockK
    private lateinit var moviesLoader: MoviesLoader

    private lateinit var testFavoriteMoviesIds: MutableStateFlow<Set<Long>>

    private lateinit var testMoviesStateFlow: MutableStateFlow<LoadingState<MoviesState>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        testFavoriteMoviesIds = MutableStateFlow(emptySet())
        every { observeFavoriteMoviesUseCase() } returns testFavoriteMoviesIds

        mockkObject(MoviesLoader.Companion)
        every { MoviesLoader.create(any(), any()) } returns moviesLoader

        testMoviesStateFlow =
            MutableStateFlow(LoadingState.Idle(MoviesState(movies = emptyList(), hasMore = false)))
        every { moviesLoader.stateFlow } returns testMoviesStateFlow
    }

    private fun subject() = MovieListViewModel(
        getMoviesPageUseCase = getMoviesPageUseCase,
        observeFavoriteMoviesUseCase = observeFavoriteMoviesUseCase,
    )

    @Test
    fun `WHEN instance is created THEN creates MoviesLoader with default query`() =
        runBlockingTest {
            val defaultQuery = MovieQuery(
                type = MovieQuery.Type.ByCategory(MovieCategory.NOW_PLAYING),
                sortBy = MovieQuery.SortBy.POPULARITY_DESCENDING,
                genres = emptySet(),
            )

            subject()

            verify {
                MoviesLoader.create(
                    query = defaultQuery,
                    getMoviesPageUseCase,
                )
            }
        }

    @Test
    fun `WHEN instance is created THEN calls MoviesLoader's loadMore`() {
        subject()

        coVerifyCalledOnes { moviesLoader.loadMore() }
    }

    @Test
    fun `WHEN requestLoadMore is called THEN calls MoviesLoader's loadMore`() {
        subject().requestLoadMore()

        // first moviesLoader.loadMore() is called during creating an instance
        coVerify(exactly = 2) { moviesLoader.loadMore() }
    }

    @Test
    fun `WHEN MoviesLoader emits state THEN subject emits state`() = runBlockingTest {
        val testMovieList = listOf<Movie>(
            mockk(),
            mockk(),
        )
        val loadingState1 = LoadingState.Loading(
            MoviesState(
                movies = testMovieList,
                hasMore = true,
            )
        )
        val loadingState2 = LoadingState.Failure(
            MoviesState(
                movies = testMovieList,
                hasMore = false,
            ),
            cause = mockk()
        )
        subject().moviesFlow.verify(this@runBlockingTest) {
            verifyItem(LoadingState.Idle(MoviesState(movies = emptyList(), hasMore = false)))
            verifyItem(loadingState1)
            verifyItem(loadingState2)
        }

        testMoviesStateFlow.emit(loadingState1)
        testMoviesStateFlow.emit(loadingState2)
    }
}
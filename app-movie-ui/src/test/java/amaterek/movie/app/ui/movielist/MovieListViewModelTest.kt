package amaterek.movie.app.ui.movielist

import amaterek.base.test.android.ViewModelTest
import amaterek.base.test.coVerifyCalledOnes
import amaterek.base.test.verify
import amaterek.movie.app.ui.common.model.UiMovie
import amaterek.movie.app.ui.common.model.UiMovieCategory
import amaterek.movie.app.ui.common.model.toUiModel
import amaterek.movie.base.LoadingState
import amaterek.movie.base.moviesloader.MoviesLoader
import amaterek.movie.base.moviesloader.MoviesLoaderState
import amaterek.movie.domain.model.Movie
import amaterek.movie.domain.model.MovieCategory
import amaterek.movie.domain.model.MovieQuery
import amaterek.movie.domain.usecase.GetMoviesPageUseCase
import amaterek.movie.domain.usecase.ObserveFavoriteMoviesUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
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

    private lateinit var testMoviesLoadingStateFlow: MutableStateFlow<LoadingState<MoviesLoaderState>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        testFavoriteMoviesIds = MutableStateFlow(emptySet())
        every { observeFavoriteMoviesUseCase() } returns testFavoriteMoviesIds

        mockkObject(MoviesLoader.Companion)
        every { MoviesLoader.create(any(), any()) } returns moviesLoader

        testMoviesLoadingStateFlow = MutableStateFlow(LoadingState.Idle(MoviesLoaderState.Empty))
        every { moviesLoader.stateFlow } returns testMoviesLoadingStateFlow
    }

    @After
    fun tearDown() {
        unmockkObject(MoviesLoader.Companion)
    }

    private fun subject() = MovieListViewModel(
        getMoviesPageUseCase = getMoviesPageUseCase,
        observeFavoriteMoviesUseCase = observeFavoriteMoviesUseCase,
    )

    private val defaultQuery = MovieQuery(
        type = MovieQuery.Type.ByCategory(MovieCategory.NOW_PLAYING),
        sortBy = MovieQuery.SortBy.POPULARITY_DESCENDING,
        genres = emptySet(),
    )

    private val defaultUiCategory = UiMovieCategory.NOW_PLAYING

    @Test
    fun `WHEN instance is created THEN creates MoviesLoader with default query`() =
        runBlockingTest {
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
        mockkStatic("amaterek.movie.app.ui.common.model.UiMovieKt") {
            val testMovieList = listOf<Movie>(mockk(), mockk())
            val testUiMovieList = listOf<UiMovie>(mockk(), mockk())
            val loadingState1 = LoadingState.Loading(
                MoviesLoaderState(
                    movies = testMovieList,
                    loadedPages = 0,
                    totalPages = 0,
                )
            )
            val loadingState2 = LoadingState.Failure(
                MoviesLoaderState(
                    movies = testMovieList,
                    loadedPages = 0,
                    totalPages = 0,
                ),
                cause = mockk()
            )

            every { testMovieList.toUiModel() } returns testUiMovieList


            subject().stateFlow.verify(this) {
                verifyItem(
                    MovieListState(
                        category = defaultUiCategory,
                        movies = emptyList(),
                        loadingState = LoadingState.Idle(Unit)
                    )
                )
                verifyItem(
                    MovieListState(
                        category = defaultUiCategory,
                        movies = testUiMovieList,
                        loadingState = LoadingState.Loading(Unit)
                    )
                )
                verifyItem(
                    MovieListState(
                        category = defaultUiCategory,
                        movies = testUiMovieList,
                        loadingState = LoadingState.Failure(Unit, loadingState2.cause),
                    )
                )
            }

            testMoviesLoadingStateFlow.value = loadingState1
            testMoviesLoadingStateFlow.value = loadingState2
        }
    }

    @Test
    fun `WHEN setCategory is called with new one THEN new MoviesLoader is created and loadMore is called on it`() =
        runBlockingTest {
            val newQuery =
                defaultQuery.copy(type = MovieQuery.Type.ByCategory(MovieCategory.POPULAR))
            val newMoviesLoader = mockk<MoviesLoader>()
            every { newMoviesLoader.stateFlow } returns testMoviesLoadingStateFlow
            coEvery { newMoviesLoader.loadMore() } just runs

            every {
                MoviesLoader.create(
                    query = newQuery,
                    getMoviesPageUseCase = getMoviesPageUseCase,
                )
            } returns newMoviesLoader

            subject().setCategory(UiMovieCategory.POPULAR)

            verify {
                MoviesLoader.create(
                    query = newQuery,
                    getMoviesPageUseCase = getMoviesPageUseCase,
                )
            }
            coVerify { newMoviesLoader.loadMore() }
        }

    @Test
    fun `WHEN setCategory is called with the same one THEN does nothing`() =
        runBlockingTest {
            subject().apply {
                every {
                    MoviesLoader.create(
                        query = any(),
                        getMoviesPageUseCase = any(),
                    )
                } throws Exception("Test failed")

                setCategory(defaultUiCategory)
            }
        }
}
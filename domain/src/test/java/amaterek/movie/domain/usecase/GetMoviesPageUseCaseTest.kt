package amaterek.movie.domain.usecase

import amaterek.base.test.CoroutineTest
import amaterek.base.test.coVerifyCalledOnes
import amaterek.movie.domain.common.FailureCause
import amaterek.movie.domain.common.QueryResult
import amaterek.movie.domain.model.MovieQuery
import amaterek.movie.domain.model.MovieList
import amaterek.movie.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetMoviesPageUseCaseTest : CoroutineTest() {

    private lateinit var subject: GetMoviesPageUseCase

    @MockK
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        subject = GetMoviesPageUseCase(movieRepository)
    }

    private val testPage = 31

    @Test
    fun `WHEN use case is invoked THEN returns repository's getMovieList success result`() =
        runBlockingTest {
            val testMovieList = mockk<MovieList>()
            val testQuery = mockk<MovieQuery>()
            coEvery {
                movieRepository.getMovieListPage(testQuery, testPage)
            } returns QueryResult.Success(testMovieList)

            assertEquals(QueryResult.Success(testMovieList), subject(testQuery, testPage))

            coVerifyCalledOnes { movieRepository.getMovieListPage(testQuery, testPage) }
        }

    @Test
    fun `WHEN use case is invoked THEN returns repository's getMovieList failure result`() =
        runBlockingTest {
            val testQuery = mockk<MovieQuery>()
            val testError = mockk<FailureCause>()
            coEvery {
                movieRepository.getMovieListPage(testQuery, testPage)
            } returns QueryResult.Failure(testError)

            assertEquals(QueryResult.Failure(testError), subject(testQuery, testPage))

            coVerifyCalledOnes { movieRepository.getMovieListPage(testQuery, testPage) }
        }
}
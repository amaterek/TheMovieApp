package amaterek.movie.domain.usecase

import amaterek.base.test.CoroutineTest
import amaterek.base.test.coVerifyCalledOnes
import amaterek.movie.domain.common.FailureCause
import amaterek.movie.domain.common.QueryResult
import amaterek.movie.domain.model.Movie
import amaterek.movie.domain.model.MovieDetails
import amaterek.movie.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetMoviesDetailsUseCaseTest : CoroutineTest() {

    private lateinit var subject: GetMovieDetailsUseCase

    @MockK
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        subject = GetMovieDetailsUseCase(movieRepository)
    }

    private val testMovieId = 367866L

    @Test
    fun `WHEN use case is invoked THEN returns repository's getMovieDetails success result`() =
        runBlockingTest {
            val testMovie = mockk<Movie> { every { id } returns testMovieId }
            val testMovieDetails = MovieDetails(
                movie = testMovie,
                description = "Test movie description",
                budget = 1_000_000L
            )
            coEvery { movieRepository.getMovieDetails(testMovieId) } returns QueryResult.Success(
                testMovieDetails
            )

            assertEquals(QueryResult.Success(testMovieDetails), subject(testMovieId))

            coVerifyCalledOnes { movieRepository.getMovieDetails(testMovieId) }
        }

    @Test
    fun `WHEN use case is invoked THEN returns repository's getMovieDetails failure result`() =
        runBlockingTest {
            val testMovie = mockk<Movie> { every { id } returns testMovieId }
            val testError = mockk<FailureCause>()
            coEvery { movieRepository.getMovieDetails(testMovieId) } returns QueryResult.Failure(
                testError
            )

            assertEquals(QueryResult.Failure(testError), subject.invoke(testMovieId))

            coVerifyCalledOnes { movieRepository.getMovieDetails(testMovieId) }
        }
}
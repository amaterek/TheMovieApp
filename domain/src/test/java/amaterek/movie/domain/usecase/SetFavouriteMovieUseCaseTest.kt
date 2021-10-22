package amaterek.movie.domain.usecase

import amaterek.base.test.CoroutineTest
import amaterek.base.test.coVerifyCalledOnes
import amaterek.movie.domain.model.Movie
import amaterek.movie.domain.repository.MovieRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class SetFavouriteMovieUseCaseTest : CoroutineTest() {

    private lateinit var subject: SetFavouriteMovieUseCase

    @MockK
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        subject = SetFavouriteMovieUseCase(movieRepository)
    }

    private val testMovieId = 345678L

    @Test
    fun `WHEN use case is invoked with isFavourite set to true THEN calls repository's setFavorite method`() =
        runBlockingTest {
            `assert that repository's setFavorite is called with`(true)
        }

    @Test
    fun `WHEN use case is invoked with isFavourite set to false THEN calls repository's setFavorite method`() =
        runBlockingTest {
            `assert that repository's setFavorite is called with`(false)
        }

    private suspend fun `assert that repository's setFavorite is called with`(isFavourite: Boolean) {
        val testMovie = mockk<Movie> { every { id } returns testMovieId }
        coEvery { movieRepository.setFavorite(testMovieId, isFavourite) } just runs

        subject(testMovie, isFavourite)

        coVerifyCalledOnes { movieRepository.setFavorite(testMovieId, isFavourite) }
    }
}
package amaterek.movie.domain.usecase

import amaterek.base.test.CoroutineTest
import amaterek.movie.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import kotlin.test.assertSame

class ObserveFavoriteMoviesUseCaseTest : CoroutineTest() {

    private lateinit var subject: ObserveFavoriteMoviesUseCase

    @MockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var testFavoriteMoviesIdsFlow: MutableStateFlow<Set<Long>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        testFavoriteMoviesIdsFlow = MutableStateFlow(emptySet())
        every { movieRepository.favoriteMoviesIds } returns testFavoriteMoviesIdsFlow

        subject = ObserveFavoriteMoviesUseCase(movieRepository)
    }

    @Test
    fun `WHEN use case is invoked THEN returns repository's favoriteMoviesIds flow`() {
        assertSame(testFavoriteMoviesIdsFlow, subject())
    }
}
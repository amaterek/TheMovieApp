package amaterek.movie.data

import amaterek.base.test.coVerifyCalledOnes
import amaterek.base.test.verify
import amaterek.movie.data.local.FavoriteMovieEntity
import amaterek.movie.data.local.FavoriteMoviesDao
import amaterek.movie.data.local.MoviesDatabase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FavoriteMoviesStateTest {

    @MockK
    private lateinit var moviesDatabase: MoviesDatabase

    @MockK
    private lateinit var favoriteMoviesDao: FavoriteMoviesDao

    private lateinit var subject: FavoriteMoviesState

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { moviesDatabase.getFavoriteMoviesDao() } returns favoriteMoviesDao

        subject = FavoriteMoviesState(
            moviesDatabase = moviesDatabase
        )
    }

    @Test
    fun `WHEN getIds is called many times THEN fetches Ids from database only once and emits state once`() =
        runBlockingTest {
            coEvery { favoriteMoviesDao.getAll() } returns testFavoriteMovieEntities

            subject.asStateFlow().verify(this) {
                verifyItem(emptySet())
                verifyItem(testFavoriteIds)
            }

            assertEquals(testFavoriteIds, subject.getIds())
            assertEquals(testFavoriteIds, subject.getIds())
            assertEquals(testFavoriteIds, subject.getIds())

            coVerifyCalledOnes { favoriteMoviesDao.getAll() }
        }

    @Test
    fun `WHEN addId is called many times THEN fetches Ids from database only once and emits new state every time`() =
        runBlockingTest {
            val newIds = listOf(54678L, 897234L, 123654L)
            coEvery { favoriteMoviesDao.getAll() } returns testFavoriteMovieEntities
            newIds.forEach { coEvery { favoriteMoviesDao.insertOrUpdate(FavoriteMovieEntity(it)) } just runs }

            subject.asStateFlow().verify(this) {
                verifyItem(emptySet())
                verifyItem(testFavoriteIds)
                for (idx in newIds.indices) {
                    verifyItem(testFavoriteIds + newIds.subList(0, idx + 1))
                }
            }

            newIds.forEach { subject.addId(it) }

            coVerifyCalledOnes { favoriteMoviesDao.getAll() }
            newIds.forEach {
                coVerifyCalledOnes {
                    favoriteMoviesDao.insertOrUpdate(
                        FavoriteMovieEntity(it)
                    )
                }
            }
        }

    @Test
    fun `WHEN removeId is called many times THEN fetches Ids from database only once and emits new state every time`() =
        runBlockingTest {
            val toDelete = listOf(3L, 1L, 8L)
            coEvery { favoriteMoviesDao.getAll() } returns testFavoriteMovieEntities
            toDelete.forEach { coEvery { favoriteMoviesDao.deleteById(it) } just runs }

            subject.asStateFlow().verify(this) {
                verifyItem(emptySet())
                verifyItem(testFavoriteIds)
                for (idx in toDelete.indices) {
                    verifyItem(testFavoriteIds - toDelete.subList(0, idx + 1))
                }
            }

            toDelete.forEach { subject.removeId(it) }

            coVerifyCalledOnes { favoriteMoviesDao.getAll() }
            toDelete.forEach { coVerifyCalledOnes { favoriteMoviesDao.deleteById(it) } }
        }

    private val testFavoriteMovieEntities = listOf(
        FavoriteMovieEntity(id = 1),
        FavoriteMovieEntity(id = 3),
        FavoriteMovieEntity(id = 8),
        FavoriteMovieEntity(id = 83287),
    )

    private val testFavoriteIds = setOf(1L, 3L, 8L, 83287L)
}
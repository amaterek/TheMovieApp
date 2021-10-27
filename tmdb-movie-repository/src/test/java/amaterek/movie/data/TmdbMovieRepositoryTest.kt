package amaterek.movie.data

import amaterek.base.locale.LocaleProvider
import amaterek.base.test.coVerifyCalledOnes
import amaterek.base.test.verify
import amaterek.movie.data.tmdb.TmdbApiService
import amaterek.movie.data.tmdb.TmdbConfig
import amaterek.movie.data.tmdb.TmdbMovieDetails
import amaterek.movie.data.tmdb.TmdbMovieListPage
import amaterek.movie.data.tmdb.mapper.toDomain
import amaterek.movie.data.tmdb.mapper.toTmdb
import amaterek.movie.domain.common.FailureCause
import amaterek.movie.domain.common.QueryResult
import amaterek.movie.domain.model.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class TmdbMovieRepositoryTest {

    @MockK
    private lateinit var service: TmdbApiService

    @MockK
    private lateinit var localeProvider: LocaleProvider

    @MockK
    private lateinit var favoriteMoviesIdsState: FavoriteMoviesState

    private lateinit var subject: TmdbMovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        subject = TmdbMovieRepository(
            service = service,
            config = config,
            localeProvider = localeProvider,
            favoriteMoviesIdsState = favoriteMoviesIdsState,
        )
    }

    @Test
    fun `WHEN getMovieListPage is called with category THEN calls service's getMovieList and returns proper value`() =
        runBlockingTest {
            mockkStatic(*tmdbMappersClass) {
                val query = mockQueryByCategory()
                val page = 2314
                val result = mockk<MovieList>()

                val tmdbLanguage = mockLocale()
                val tmdbServiceResult = mockServiceResultSuccess(result = result)

                coEvery {
                    service.getMovieList(
                        type = tmdbCategory,
                        apiKey = config.apiKey,
                        sortBy = tmdbSortBy,
                        genres = tmdbGenres,
                        language = tmdbLanguage,
                        page = page
                    )
                } returns tmdbServiceResult

                assertEquals(
                    QueryResult.Success(result),
                    subject.getMovieListPage(query = query, page = page)
                )

                coVerifyCalledOnes {
                    service.getMovieList(
                        type = tmdbCategory,
                        apiKey = config.apiKey,
                        sortBy = tmdbSortBy,
                        genres = tmdbGenres,
                        language = tmdbLanguage,
                        page = page
                    )
                }
            }
        }

    @Test
    fun `WHEN service's getMovieList fails for category THEN calls getMovieListPage returns Failure with Error cause`() =
        runBlockingTest {
            mockkStatic(*tmdbMappersClass) {
                coEvery {
                    service.getMovieList(
                        type = any(),
                        apiKey = any(),
                        sortBy = any(),
                        genres = any(),
                        language = any(),
                        page = any()
                    )
                } throws Exception()
                every { localeProvider.getLocale() } returns mockk()

                assertEquals(
                    QueryResult.Failure(FailureCause.Error),
                    subject.getMovieListPage(
                        query = MovieQuery(
                            type = MovieQuery.Type.ByCategory(MovieCategory.NOW_PLAYING),
                            sortBy = MovieQuery.SortBy.POPULARITY_DESCENDING,
                            genres = emptySet(),
                        ), page = 1
                    )
                )
            }
        }

    @Test
    fun `WHEN getMovieListPage is called with search by phrase THEN calls service's searchMovie`() =
        runBlockingTest {
            mockkStatic(*tmdbMappersClass) {
                val phrase = "the search phrase"
                val query = mockQueryByPhrase(phrase)
                val page = 214
                val result = mockk<MovieList>()

                val tmdbLanguage = mockLocale()
                val tmdbServiceResult = mockServiceResultSuccess(result)

                coEvery {
                    service.searchMovie(
                        phrase = phrase,
                        apiKey = config.apiKey,
                        language = tmdbLanguage,
                        page = page
                    )
                } returns tmdbServiceResult

                assertEquals(
                    QueryResult.Success(result),
                    subject.getMovieListPage(query = query, page = page)
                )

                coVerifyCalledOnes {
                    service.searchMovie(
                        phrase = phrase,
                        apiKey = config.apiKey,
                        language = tmdbLanguage,
                        page = page
                    )
                }
            }
        }

    @Test
    fun `WHEN getMovieListPage is called with search by phrase an the phrase is blank THEN returns empty page`() =
        runBlockingTest {
            val query = mockQueryByPhrase("   ")
            val page = 23423

            assertEquals(
                QueryResult.Success(
                    MovieList(
                        items = emptyList(),
                        loadedPages = 1,
                        totalPages = 1,
                    )
                ),
                subject.getMovieListPage(query = query, page = page)
            )
        }

    @Test
    fun `WHEN service's getMovieList for search by phrase fails THEN calls getMovieListPage returns Failure with Error cause`() =
        runBlockingTest {
            mockkStatic(*tmdbMappersClass) {
                coEvery {
                    service.getMovieList(
                        type = any(),
                        apiKey = any(),
                        sortBy = any(),
                        genres = any(),
                        language = any(),
                        page = any()
                    )
                } throws Exception()
                every { localeProvider.getLocale() } returns mockk()

                assertEquals(
                    QueryResult.Failure(FailureCause.Error),
                    subject.getMovieListPage(
                        query = MovieQuery(
                            type = MovieQuery.Type.ByPhrase("the search phrase"),
                            sortBy = MovieQuery.SortBy.POPULARITY_DESCENDING,
                            genres = emptySet(),
                        ), page = 1
                    )
                )
            }
        }

    @Test
    fun `WHEN getMovieDetails is called and the movie's id is in favorites THEN calls service's getMovie and movie is favorite`() =
        runBlockingTest {
            mockkStatic(*tmdbMappersClass) {
                val movieId = 5467889L
                val result = mockk<MovieDetails>()

                val tmdbLanguage = mockLocale()
                val tmdbServiceResult = mockk<TmdbMovieDetails>()
                mockFavoriteMoviesIds(setOf(movieId))

                every {
                    tmdbServiceResult.toDomain(
                        basePosterImagePath = config.basePosterImageUrl,
                        baseBackdropImagePath = config.baseBackdropImageUrl,
                        isFavorite = true
                    )
                } returns result

                coEvery {
                    service.getMovie(
                        id = movieId,
                        apiKey = config.apiKey,
                        language = tmdbLanguage,
                    )
                } returns tmdbServiceResult

                assertEquals(
                    QueryResult.Success(result),
                    subject.getMovieDetails(movieId = movieId)
                )

                coVerifyCalledOnes {
                    service.getMovie(
                        id = movieId,
                        apiKey = config.apiKey,
                        language = tmdbLanguage,
                    )
                }
            }
        }

    @Test
    fun `WHEN getMovieDetails is called and the movie's id is not in favorites THEN calls service's getMovie and movie is not favorite`() =
        runBlockingTest {
            mockkStatic(*tmdbMappersClass) {
                val movieId = 5467889L
                val result = mockk<MovieDetails>()

                val tmdbLanguage = mockLocale()
                val tmdbServiceResult = mockk<TmdbMovieDetails>()
                mockFavoriteMoviesIds(setOf(movieId + 1))

                every {
                    tmdbServiceResult.toDomain(
                        basePosterImagePath = config.basePosterImageUrl,
                        baseBackdropImagePath = config.baseBackdropImageUrl,
                        isFavorite = false
                    )
                } returns result

                coEvery {
                    service.getMovie(
                        id = movieId,
                        apiKey = config.apiKey,
                        language = tmdbLanguage,
                    )
                } returns tmdbServiceResult

                assertEquals(
                    QueryResult.Success(result),
                    subject.getMovieDetails(movieId = movieId)
                )

                coVerifyCalledOnes {
                    service.getMovie(
                        id = movieId,
                        apiKey = config.apiKey,
                        language = tmdbLanguage,
                    )
                }
            }
        }

    @Test
    fun `WHEN service's getMovie fails THEN calls getMovieDetails returns Failure with Error cause`() =
        runBlockingTest {
            mockkStatic(*tmdbMappersClass) {
                val movieId = 546789L
                val tmdbLanguage = mockLocale()

                coEvery {
                    service.getMovie(
                        id = movieId,
                        apiKey = config.apiKey,
                        language = tmdbLanguage,
                    )
                } throws Exception()

                assertEquals(
                    QueryResult.Failure(cause = FailureCause.Error),
                    subject.getMovieDetails(movieId = movieId)
                )

                coVerifyCalledOnes {
                    service.getMovie(
                        id = movieId,
                        apiKey = config.apiKey,
                        language = tmdbLanguage,
                    )
                }
            }
        }

    @Test
    fun `WHEN setFavorite is called THEN repositories flow emits new favorite movies set`() =
        runBlockingTest {
            val testIdsFlow = MutableStateFlow<Set<Long>>(emptySet())
            every { favoriteMoviesIdsState.asStateFlow() } returns testIdsFlow
            coEvery { favoriteMoviesIdsState.getIds() } returns testIdsFlow.value
            coEvery { favoriteMoviesIdsState.addId(any()) } answers {
                testIdsFlow.value += arg<Long>(0)
            }
            coEvery { favoriteMoviesIdsState.removeId(any()) } answers {
                testIdsFlow.value -= arg<Long>(0)
            }

            subject.favoriteMoviesIds.verify(this) {
                verifyItem(emptySet())
                verifyItem(setOf(1L))
                verifyItem(setOf(1L, 2L))
                verifyItem(setOf(1L, 2L, 3L))
                verifyItem(setOf(1L, 3L))
                verifyItem(setOf(3L))
            }

            subject.setFavorite(1L, true)
            subject.setFavorite(2L, true)
            subject.setFavorite(3L, true)
            subject.setFavorite(2L, false)
            subject.setFavorite(1L, false)
        }

    private fun mockQueryByCategory(): MovieQuery {
        val category = mockk<MovieCategory>()
        val sortBy = mockk<MovieQuery.SortBy>()
        val genres = mockk<Set<MovieGenre>>()

        every { category.toTmdb() } returns tmdbCategory
        every { sortBy.toTmdb() } returns tmdbSortBy
        every { genres.toTmdb() } returns tmdbGenres

        return MovieQuery(
            type = MovieQuery.Type.ByCategory(category),
            sortBy = sortBy,
            genres = genres,
        )
    }

    private fun mockQueryByPhrase(phrase: String): MovieQuery {
        val sortBy = mockk<MovieQuery.SortBy>()
        val genres = mockk<Set<MovieGenre>>()
        return MovieQuery(
            type = MovieQuery.Type.ByPhrase(phrase),
            sortBy = sortBy,
            genres = genres,
        )
    }

    private fun mockServiceResultSuccess(
        result: MovieList,
        testFavoriteMoviesIds: Set<Long> = mockFavoriteMoviesIds(mockk())
    ): TmdbMovieListPage {
        val tmdbServiceResult = mockk<TmdbMovieListPage>()

        every {
            tmdbServiceResult.toDomain(
                basePosterImagePath = config.basePosterImageUrl,
                baseBackdropImagePath = config.baseBackdropImageUrl,
                favoriteMoviesIds = testFavoriteMoviesIds,
            )
        } returns result

        return tmdbServiceResult
    }

    private fun mockFavoriteMoviesIds(testFavoriteMoviesIds: Set<Long>): Set<Long> {
        every { favoriteMoviesIdsState.asStateFlow().value } returns testFavoriteMoviesIds
        coEvery { favoriteMoviesIdsState.getIds() } returns testFavoriteMoviesIds
        return testFavoriteMoviesIds
    }

    private fun mockLocale(): String {
        val locale = mockk<Locale>()
        every { locale.toTmdb() } returns tmdbLanguage
        every { localeProvider.getLocale() } returns locale
        return tmdbLanguage
    }

    private val tmdbCategory = "tmdbCategory"
    private val tmdbSortBy = "tmdbSortBy"
    private val tmdbGenres = "tmdbGenres"
    private val tmdbLanguage = "tmdbLanguage"

    private val config = TmdbConfig(
        baseUrl = "the baseUrl",
        basePosterImageUrl = "the basePosterImageUrl",
        baseBackdropImageUrl = "the baseBackdropImageUrl",
        apiKey = "the apiKey",
    )

    private val tmdbMappersClass = arrayOf(
        "amaterek.movie.data.tmdb.mapper.TmdbCategoryMappersKt",
        "amaterek.movie.data.tmdb.mapper.TmdbGenreMappersKt",
        "amaterek.movie.data.tmdb.mapper.TmdbMovieMappersKt",
        "amaterek.movie.data.tmdb.mapper.TmdbSortByMappersKt",
    )
}
package amaterek.movie.data.tmdb.mapper

import amaterek.movie.data.tmdb.*
import amaterek.movie.domain.model.*
import amaterek.movie.domain.model.MovieGenre.*
import io.mockk.*
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class TmdbGenreMappersTest {

    private val tmdbGenreMap = mapOf(
        12 to listOf(ADVENTURE),
        14 to listOf(FANTASY),
        16 to listOf(ANIMATION),
        18 to listOf(DRAMA),
        27 to listOf(HORROR),
        28 to listOf(ACTION),
        35 to listOf(COMEDY),
        36 to listOf(HISTORY),
        37 to listOf(WESTERN),
        53 to listOf(THRILLER),
        80 to listOf(CRIME),
        99 to listOf(DOCUMENTARY),
        878 to listOf(SCIENCE_FICTION),
        9648 to listOf(MYSTERY),
        10402 to listOf(MUSIC),
        10749 to listOf(ROMANCE),
        10751 to listOf(FAMILY),
        10752 to listOf(WAR),
        10759 to listOf(ACTION, ADVENTURE),
        10762 to listOf(KIDS),
        10763 to listOf(NEWS),
        10764 to listOf(REALITY),
        10765 to listOf(SCIENCE_FICTION, FANTASY),
        10766 to listOf(SOAP),
        10767 to listOf(TALK),
        10768 to listOf(WAR, POLITICS),
        10770 to listOf(TV_MOVIE),
    )

    @Test
    fun `maps TMDB genre id to MovieGenre list`() {
        for (tmdbGenreId in -1..20_000) {
            tmdbGenreMap[tmdbGenreId]?.let { expected ->
                assertEquals(expected, tmdbGenreId.toDomainGenre())
            } ?: assertEquals(emptyList(), tmdbGenreId.toDomainGenre())
        }
        assertEquals(emptyList(), null.toDomainGenre())
    }

    @Test
    fun `maps MovieGenre to TMDB genre id`() {
        MovieGenre.values().forEach { movieGenre ->
            if (movieGenre == POLITICS) return@forEach // This one has no mapping
            val tmdbGenreId = movieGenre.toTmdb()
            val movieGenres = tmdbGenreMap[tmdbGenreId]
            assertEquals(listOf(movieGenre), movieGenres)
        }
    }

    @Test
    fun `maps TMDB genres ids to MovieGenre list`() {
        mockkStatic(tmdbGenreMappersClass) {
            val tmdbGenreIdsMap = mapOf<Int, List<MovieGenre>>(
                154 to listOf(mockk()),
                3984 to listOf(mockk(), mockk()),
                83245 to listOf(mockk()),
            )
            tmdbGenreIdsMap.forEach { (tmdbGenreId, movieGenre) -> every { tmdbGenreId.toDomainGenre() } returns movieGenre }

            assertEquals(
                tmdbGenreIdsMap.values.reduce { acc, list -> acc + list },
                tmdbGenreIdsMap.keys.toList().getDomainGenres()
            )
        }
    }

    @Test
    fun `maps TmdbMovieDetailsGenre to MovieGenre list`() {
        mockkStatic(tmdbGenreMappersClass) {
            val genre1 = mockk<MovieGenre>()
            val genre2 = mockk<MovieGenre>()
            val genre3 = mockk<MovieGenre>()
            every { 8433209.toDomainGenre() } returns listOf(genre1, genre2)
            every { 342523.toDomainGenre() } returns listOf(genre3)

            assertEquals(
                listOf(genre1, genre2, genre3),
                listOf(
                    TmdbMovieDetailsGenre(id = 8433209, "8433209"),
                    TmdbMovieDetailsGenre(id = 342523, "342523"),
                ).toDomainGenres()
            )
        }
    }

    private val tmdbGenreMappersClass = "amaterek.movie.data.tmdb.mapper.TmdbGenreMappersKt"
}
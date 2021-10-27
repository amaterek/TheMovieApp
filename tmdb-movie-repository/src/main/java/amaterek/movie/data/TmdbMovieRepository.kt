package amaterek.movie.data

import amaterek.base.locale.LocaleProvider
import amaterek.base.log.Log
import amaterek.base.logTag
import amaterek.movie.data.tmdb.TmdbApiService
import amaterek.movie.data.tmdb.TmdbConfig
import amaterek.movie.data.tmdb.mapper.toDomain
import amaterek.movie.data.tmdb.mapper.toTmdb
import amaterek.movie.domain.common.FailureCause
import amaterek.movie.domain.common.QueryResult
import amaterek.movie.domain.model.MovieCategory
import amaterek.movie.domain.model.MovieDetails
import amaterek.movie.domain.model.MovieList
import amaterek.movie.domain.model.MovieQuery
import amaterek.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class TmdbMovieRepository @Inject constructor(
    private val service: TmdbApiService,
    private val config: TmdbConfig,
    private val localeProvider: LocaleProvider,
    private val favoriteMoviesIdsState: FavoriteMoviesState,
) : MovieRepository {

    override val favoriteMoviesIds: StateFlow<Set<Long>>
        get() = favoriteMoviesIdsState.asStateFlow()

    override suspend fun getMovieListPage(query: MovieQuery, page: Int): QueryResult<MovieList> =
        when (val queryType = query.type) {
            is MovieQuery.Type.ByCategory -> getMoviesByCategory(queryType.category, query, page)
            is MovieQuery.Type.ByPhrase -> getMoviesByPhrase(queryType.phrase, page)
        }

    private suspend fun getMoviesByCategory(
        category: MovieCategory,
        query: MovieQuery,
        page: Int
    ) = try {
        QueryResult.Success(
            service.getMovieList(
                type = category.toTmdb(),
                apiKey = config.apiKey,
                sortBy = query.sortBy.toTmdb(),
                genres = query.genres.toTmdb(),
                language = localeProvider.getLocale().toTmdb(),
                page = page,
            ).toDomain(
                config.basePosterImageUrl,
                config.baseBackdropImageUrl,
                favoriteMoviesIdsState.getIds()
            )
        )
    } catch (e: Exception) {
        Log.w(logTag(), throwable = e)
        QueryResult.Failure(cause = FailureCause.Error)
    }

    private suspend fun getMoviesByPhrase(
        phrase: String,
        page: Int
    ) = try {
        val trimmedPhrase = phrase.trim()
        if (trimmedPhrase.isEmpty()) {
            QueryResult.Success(
                MovieList(
                    items = emptyList(),
                    loadedPages = 1,
                    totalPages = 1,
                )
            )
        } else {
            QueryResult.Success(
                service.searchMovie(
                    phrase = trimmedPhrase,
                    apiKey = config.apiKey,
                    language = localeProvider.getLocale().toTmdb(),
                    page = page,
                ).toDomain(
                    config.basePosterImageUrl,
                    config.baseBackdropImageUrl,
                    favoriteMoviesIdsState.getIds()
                )
            )
        }
    } catch (e: Exception) {
        Log.w(logTag(), throwable = e)
        QueryResult.Failure(cause = FailureCause.Error)
    }

    override suspend fun getMovieDetails(movieId: Long): QueryResult<MovieDetails> =
        try {
            val result =
                service.getMovie(movieId, config.apiKey, localeProvider.getLocale().toTmdb())
                    .toDomain(
                        config.basePosterImageUrl,
                        config.baseBackdropImageUrl,
                        isFavorite(movieId)
                    )
            QueryResult.Success(result)
        } catch (e: Exception) {
            Log.w(logTag(), throwable = e)
            QueryResult.Failure(cause = FailureCause.Error)
        }

    override suspend fun setFavorite(movieId: Long, favorite: Boolean) {
        when (favorite) {
            true -> favoriteMoviesIdsState.addId(movieId)
            false -> favoriteMoviesIdsState.removeId(movieId)
        }
    }

    private suspend fun isFavorite(movieId: Long) =
        favoriteMoviesIdsState.getIds().contains(movieId)
}

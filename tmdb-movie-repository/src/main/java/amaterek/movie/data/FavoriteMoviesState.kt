package amaterek.movie.data

import amaterek.movie.data.local.FavoriteMovieEntity
import amaterek.movie.data.local.MoviesDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class FavoriteMoviesState @Inject constructor(
    private val moviesDatabase: MoviesDatabase
) {

    private val stateFlow: MutableStateFlow<Set<Long>> = MutableStateFlow(emptySet())

    private var isInitialized = false

    private suspend fun loadFavoriteIdsFromDb() {
        if (!isInitialized) {
            isInitialized = true
            stateFlow.value =
                moviesDatabase.getFavoriteMoviesDao().getAll().map { it.id }.toMutableSet()
        }
    }

    suspend fun getIds(): Set<Long> {
        loadFavoriteIdsFromDb()
        return stateFlow.value
    }

    suspend fun addId(id: Long) {
        loadFavoriteIdsFromDb()
        moviesDatabase.getFavoriteMoviesDao().insertOrUpdate(FavoriteMovieEntity(id))
        stateFlow.value += id
    }

    suspend fun removeId(id: Long) {
        loadFavoriteIdsFromDb()
        moviesDatabase.getFavoriteMoviesDao().deleteById(id)
        stateFlow.value -= id
    }

    fun asStateFlow(): StateFlow<Set<Long>> = stateFlow
}
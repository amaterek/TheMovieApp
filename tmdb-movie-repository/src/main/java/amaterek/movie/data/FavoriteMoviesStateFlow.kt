package amaterek.movie.data

import amaterek.movie.data.local.FavoriteMovieEntity
import amaterek.movie.data.local.MoviesDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class FavoriteMoviesStateFlow @Inject constructor(private val db: MoviesDatabase) :
    StateFlow<Set<Long>> {
    private var isInitialized = false
    private val internalStateFlow = MutableStateFlow(emptySet<Long>())

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<Set<Long>>) =
        internalStateFlow.collect(collector)

    override val replayCache: List<Set<Long>>
        get() = internalStateFlow.replayCache

    override val value: Set<Long>
        get() = internalStateFlow.value

    private suspend fun loadFavoriteIdsFromDb() {
        if (!isInitialized) {
            isInitialized = true
            internalStateFlow.value =
                db.getFavoriteMoviesDao().getAll().map { it.id }.toMutableSet()
        }
    }

    suspend fun getIds(): Set<Long> {
        loadFavoriteIdsFromDb()
        return internalStateFlow.value
    }

    suspend fun addId(id: Long) {
        loadFavoriteIdsFromDb()
        db.getFavoriteMoviesDao().insertOrUpdate(FavoriteMovieEntity(id))
        internalStateFlow.value += id
    }

    suspend fun removeId(id: Long) {
        loadFavoriteIdsFromDb()
        db.getFavoriteMoviesDao().deleteById(id)
        internalStateFlow.value = value - id
    }
}
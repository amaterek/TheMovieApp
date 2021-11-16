package amaterek.movie.app.ui.splash

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SplashTask @Inject constructor() {

    private val splashShowTimeMillis = 3000L

    private var job: Job? = null

    suspend fun execute(coroutineScope: CoroutineScope) {
        job = coroutineScope.launch {
            delay(splashShowTimeMillis)
        }
        job?.join()
        job = null
    }

    fun requestFinish() {
        job?.cancel()
        job = null
    }
}
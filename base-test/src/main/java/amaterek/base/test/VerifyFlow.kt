package amaterek.base.test

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
suspend fun <T> Flow<T>.verify(
    launchIn: CoroutineScope = CoroutineScope(Dispatchers.Main),
    validate: suspend FlowTurbine<T>.() -> Unit
) {
    launchIn.launch { test(timeout = Duration.ZERO, validate = validate) }
}

suspend fun <T> FlowTurbine<T>.verifyItem(item: T) =
    assertEquals(item, awaitItem())

suspend fun <T> FlowTurbine<T>.verifyComplete() = awaitComplete()
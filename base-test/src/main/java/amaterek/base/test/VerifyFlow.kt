package amaterek.base.test

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlin.test.assertEquals
import kotlin.time.Duration

interface VerifyFlowScope<T> {
    suspend fun verifyItem(item: T)

    suspend fun expectItem(): T
}

fun <T> Flow<T>.verify(
    launchIn: TestCoroutineScope,
    validate: suspend VerifyFlowScope<T>.() -> Unit
) {
    launchIn.launch {
        test(
            timeout = Duration.INFINITE,
            validate = { validateHotFlow(this, validate) }
        )
    }
}

private suspend fun <T> FlowTurbine<T>.validateHotFlow(
    flowTurbine: FlowTurbine<T>,
    validate: suspend VerifyFlowScope<T>.() -> Unit
) {
    val verifyFlowScope = VerifyFlowScopeImpl(flowTurbine)
    var expectMoreItems = true
    try {
        verifyFlowScope.validate()
        expectMoreItems = false
        awaitComplete()
    } catch (e: TimeoutCancellationException) {
        val unconsumedEvents = cancelAndConsumeRemainingEvents()
        if (unconsumedEvents.isNotEmpty()) {
            throw AssertionError("Unconsumed events: $unconsumedEvents")
        } else if (expectMoreItems) {
            throw AssertionError("Test has finished but expected items=${verifyFlowScope.formatExpectedItems()}")
        }
    }
}

private fun <T> VerifyFlowScopeImpl<T>.formatExpectedItems() =
    expectedItems.map { it ?: "Unknown" }
        .joinToString(prefix = "[", postfix = "]")

private class VerifyFlowScopeImpl<T>(private val flowTurbine: FlowTurbine<T>) : VerifyFlowScope<T> {

    val expectedItems = mutableListOf<T?>()

    override suspend fun verifyItem(item: T) {
        expectedItems.add(item)
        assertEquals(item, flowTurbine.awaitItem())
        expectedItems.removeLast()
    }

    override suspend fun expectItem(): T {
        expectedItems.add(null)
        val result = flowTurbine.awaitItem()
        expectedItems.removeLast()
        return result
    }
}
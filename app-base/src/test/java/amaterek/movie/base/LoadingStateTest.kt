package amaterek.movie.base

import amaterek.movie.domain.common.FailureCause
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

class LoadingStateTest {

    @Test
    fun `WHEN state is Idle THEN copies the state with new value`() {
        val testState = LoadingState.Idle(mockk<Any>())
        val newValue = mockk<Any>()
        assertEquals(
            LoadingState.Idle(newValue),
            testState.copy(newValue)
        )
    }

    @Test
    fun `WHEN state is Loading THEN copies the state with new value`() {
        val testState = LoadingState.Loading(mockk<Any>())
        val newValue = mockk<Any>()
        assertEquals(
            LoadingState.Loading(newValue),
            testState.copy(newValue)
        )
    }

    @Test
    fun `WHEN state is Failure THEN copies the state with new value`() {
        val testCause = mockk<FailureCause>()
        val testState = LoadingState.Failure(mockk<Any>(), testCause)
        val newValue = mockk<Any>()
        assertEquals(
            LoadingState.Failure(newValue, testCause),
            testState.copy(newValue)
        )
    }
}
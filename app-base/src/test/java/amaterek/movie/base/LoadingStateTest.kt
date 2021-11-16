package amaterek.movie.base

import amaterek.movie.domain.common.FailureCause
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

class LoadingStateTest {

    @Test
    fun `WHEN state is Idle THEN transforms the state`() {
        val testValue = mockk<Any>()
        val testState = LoadingState.Idle(testValue)
        val transformedValue = mockk<Any>()
        assertEquals(
            LoadingState.Idle(transformedValue),
            testState.transformValue { if (this == testValue) transformedValue else mockk() }
        )
    }

    @Test
    fun `WHEN state is Loading THEN transforms the state`() {
        val testValue = mockk<Any>()
        val testState = LoadingState.Loading(testValue)
        val transformedValue = mockk<Any>()
        assertEquals(
            LoadingState.Loading(transformedValue),
            testState.transformValue { if (this == testValue) transformedValue else mockk() }
        )
    }

    @Test
    fun `WHEN state is Failure THEN transforms the state`() {
        val testCause = mockk<FailureCause>()
        val testValue = mockk<Any>()
        val testState = LoadingState.Failure(testValue, testCause)
        val transformedValue = mockk<Any>()
        assertEquals(
            LoadingState.Failure(transformedValue, testCause),
            testState.transformValue { if (this == testValue) transformedValue else mockk() }
        )
    }
}
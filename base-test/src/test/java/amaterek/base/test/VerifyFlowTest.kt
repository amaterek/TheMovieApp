package amaterek.base.test

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.ComparisonFailure
import org.junit.Test
import kotlin.test.assertEquals

class VerifyFlowTest {

    class TestEventFlow {

        private val flow = MutableStateFlow("0")

        fun asFlow(): Flow<String> = flow.asSharedFlow()

        suspend fun emit(event: String) = flow.emit(event)
    }

    @Test
    fun `WHEN flow emits expected by verify items THEN test passes`() = runBlockingTest {
        with(TestEventFlow()) {
            asFlow().verify(this@runBlockingTest) {
                assertEquals("0", expectItem())
                verifyItem("1")
                assertEquals("2", expectItem())
                verifyItem("3")
            }
            emit("1")
            emit("2")
            emit("3")
        }
    }

    @Test(expected = ComparisonFailure::class)
    fun `WHEN flow emits not expected value by verify value THEN test fails`() =
        runBlockingTest {
            with(TestEventFlow()) {
                asFlow().verify(this@runBlockingTest) {
                    verifyItem("0")
                    verifyItem("1")
                    verifyItem("2")
                    verifyItem("3")
                }
                emit("1")
                emit("2")
                emit("4")
            }
        }

    @Test(expected = AssertionError::class)
    fun `WHEN flow emits value but verify does not expect any item THEN test fails`() =
        runBlockingTest {
            TestEventFlow().asFlow().verify(this) {}
        }

    @Test(expected = ComparisonFailure::class)
    fun `WHEN flow emits not expected item by verify value THEN test fails`() =
        runBlockingTest {
            with(TestEventFlow()) {
                asFlow().verify(this@runBlockingTest) {
                    verifyItem("0")
                    verifyItem("1")
                    verifyItem("2")
                    assertEquals("3", expectItem())
                }
                emit("1")
                emit("2")
                emit("4")
            }
        }

    @Test(expected = AssertionError::class)
    fun `WHEN flow emits to much values THEN test fails`() = runBlockingTest {
        with(TestEventFlow()) {
            asFlow().verify(this@runBlockingTest) {
                verifyItem("0")
                verifyItem("1")
                verifyItem("2")
            }
            emit("1")
            emit("2")
            emit("3")
        }
    }

    @Test(expected = AssertionError::class)
    fun `WHEN verify expect more values than flow emits THEN test fails`() = runBlockingTest {
        with(TestEventFlow()) {
            asFlow().verify(this@runBlockingTest) {
                verifyItem("0")
                verifyItem("1")
                verifyItem("2")
                verifyItem("3")
                verifyItem("4")
            }
            emit("1")
            emit("2")
            emit("3")
        }
    }
}

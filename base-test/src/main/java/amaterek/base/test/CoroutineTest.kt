package amaterek.base.test

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class CoroutineTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        coroutineTestRule.runBlockingTest(block)
}
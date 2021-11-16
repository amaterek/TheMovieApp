package amaterek.movie.app.ui.splash

import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertTrue

class SplashTaskTest {

    @Test
    fun `WHEN invoked is called THEN waits 3 seconds`() = runBlockingTest {
        val testScope = TestCoroutineScope()

        val job = launch { SplashTask().execute(testScope) }

        testScope.advanceTimeBy(2999)
        assertTrue(job.isActive)
        testScope.advanceTimeBy(1)
        assertTrue(job.isCompleted)
    }

    @Test
    fun `WHEN requestFinish is called THEN cancels the task`() = runBlockingTest {
        val subject = SplashTask()
        val testScope = TestCoroutineScope()
        val job = launch { subject.execute(testScope) }

        subject.requestFinish()

        assertTrue(job.isCompleted)
    }
}
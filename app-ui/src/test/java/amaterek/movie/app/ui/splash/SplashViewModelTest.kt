package amaterek.movie.app.ui.splash

import amaterek.base.test.*
import amaterek.base.test.android.ViewModelTest
import amaterek.movie.app.ui.splash.SplashViewModel.Event
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class SplashViewModelTest : ViewModelTest() {

    @MockK
    private lateinit var splashTask: SplashTask

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    private fun subject() = SplashViewModel(
        splashTask = splashTask
    )

    @Test
    fun `WHEN instance is created THEN executes splash task and emits SplashFinished event`() =
        runBlockingTest {
            coEvery { splashTask.execute(coroutineTestRule) } just runs

            val subject = subject()

            subject.eventFlow.verify(this@runBlockingTest) {
                verifyItem(Event.SplashFinished)
            }

            coVerifyCalledOnes { splashTask.execute(coroutineTestRule) }
        }

    @Test
    fun `WHEN requestFinish is called THEN finishes splash task and emits SplashFinished event`() =
        runBlockingTest {
            coEvery { splashTask.execute(coroutineTestRule) } just runs
            coEvery { splashTask.requestFinish() } just runs

            subject().apply {
                eventFlow.verify(this@runBlockingTest) {
                    verifyItem(Event.SplashFinished)
                }

                requestFinish()
            }

            coVerifyCalledOnes { splashTask.requestFinish() }
        }
}
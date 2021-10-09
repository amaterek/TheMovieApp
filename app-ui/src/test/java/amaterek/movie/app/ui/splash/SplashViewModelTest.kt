package amaterek.movie.app.ui.splash

import amaterek.base.test.android.ViewModelTest
import amaterek.base.test.coVerifyCalledOnes
import amaterek.base.test.verify
import amaterek.base.test.verifyComplete
import amaterek.base.test.verifyItem
import amaterek.movie.app.ui.splash.SplashViewModel.Event
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
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

            subject().eventFlow.verify {
                verifyItem(Event.SplashFinished)
                verifyComplete()
            }

            coVerifyCalledOnes { splashTask.execute(coroutineTestRule) }
        }

    @Test
    fun `WHEN requestFinish is called THEN finishes splash task and emits SplashFinished event`() =
        runBlockingTest {
            coEvery { splashTask.execute(coroutineTestRule) } just runs
            coEvery { splashTask.requestFinish() } just runs

            subject().apply {
                eventFlow.verify {
                    verifyItem(Event.SplashFinished)
                    verifyComplete()
                }

                requestFinish()
            }

            coVerifyCalledOnes { splashTask.requestFinish() }
        }
}
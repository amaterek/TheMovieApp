package amaterek.base.android.viewmodel

import amaterek.base.log.Log
import amaterek.base.logTag
import amaterek.base.test.verifyCalledOnes
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class BaseViewModelTest {

    class TestViewModel : BaseViewModel() {
        public override fun onCleared() {
            super.onCleared()
        }
    }

    @Before
    fun setUp() {
        mockkObject(Log)
    }

    @After
    fun tearDown() {
        unmockkObject(Log)
    }

    private fun subject() = TestViewModel()

    @Test
    fun `WHEN instance created and cleared THEN logs 'init()' and 'onCleared()' message`() {
        every { Log.v(any(), "init()") } just runs

        val subject = subject()

        verifyCalledOnes { Log.v(subject.logTag(), "init()") }
    }

    @Test
    fun `WHEN instance is about to destroy THEN logs 'onCleared()' message`() {
        every { Log.v(any(), "onCleared()") } just runs

        val subject = subject().apply { onCleared() }

        verifyCalledOnes { Log.v(subject.logTag(), "onCleared()") }
    }
}
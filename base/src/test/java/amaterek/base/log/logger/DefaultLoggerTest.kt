package amaterek.base.log.logger

import io.mockk.*
import org.junit.Before
import org.junit.Test

class DefaultLoggerTest {

    private val testTag = "testTag"
    private val testMessage = "testMessage"
    private val testThrowable = Exception("testThrowable")

    private lateinit var subject: DefaultLogger

    private val kotlinConsoleOutObject = System.out

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        subject = DefaultLogger()
    }

    @Test
    fun `WHEN v(tag, msg) is called THEN logs verbose`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.verbose(testTag, testMessage)

            verify { println("VERBOSE: $testTag: $testMessage") }
        }
    }

    @Test
    fun `WHEN v(tag, message, throwable) is called THEN logs verbose`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.verbose(testTag, testMessage, testThrowable)

            verify { println("VERBOSE: $testTag: $testMessage throwable=$testThrowable") }
        }
    }

    @Test
    fun `WHEN d(tag, message) is called THEN logs debug`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.debug(testTag, testMessage)

            verify { println("DEBUG: $testTag: $testMessage") }
        }
    }

    @Test
    fun `WHEN d(tag, message, throwable) is called THEN logs debug`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.debug(testTag, testMessage, testThrowable)

            verify {
                println("DEBUG: $testTag: $testMessage throwable=$testThrowable")
            }
        }
    }

    @Test
    fun `WHEN i(tag, message) is called THEN logs info`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.info(testTag, testMessage)

            verify { println("INFO: $testTag: $testMessage") }
        }
    }

    @Test
    fun `WHEN i(tag, message, throwable) is called THEN logs debug`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.info(testTag, testMessage, testThrowable)

            verify {
                println("INFO: $testTag: $testMessage throwable=$testThrowable")
            }
        }
    }

    @Test
    fun `WHEN w(tag, message) is called THEN logs warning`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.warning(testTag, testMessage)

            verify { println("WARN: $testTag: $testMessage") }
        }
    }

    @Test
    fun `WHEN w(tag, message, throwable) is called THEN logs warning`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.warning(testTag, testMessage, testThrowable)

            verify {
                println("WARN: $testTag: $testMessage throwable=$testThrowable")
            }
        }
    }

    @Test
    fun `WHEN e(tag, message) is called THEN logs error`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.error(testTag, testMessage)

            verify { println("ERROR: $testTag: $testMessage") }
        }
    }

    @Test
    fun `WHEN e(tag, message, throwable) is called THEN logs error`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.error(testTag, testMessage, testThrowable)

            verify {
                println("ERROR: $testTag: $testMessage throwable=$testThrowable")
            }
        }
    }

    @Test
    fun `WHEN wtf(tag, message) is called THEN logs warning`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.wtf(testTag, testMessage)

            verify { println("WTF: $testTag: $testMessage") }
        }
    }

    @Test
    fun `WHEN wtf(tag, message, throwable) is called THEN logs wtf`() {
        mockkObject(kotlinConsoleOutObject) {
            every { println(any<String>()) } just runs

            subject.wtf(testTag, testMessage, testThrowable)

            verify {
                println("WTF: $testTag: $testMessage throwable=$testThrowable")
            }
        }
    }
}
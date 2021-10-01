package amaterek.base.log

import amaterek.base.log.logger.Logger
import amaterek.base.test.verifyCalledOnes
import io.mockk.*
import org.junit.Test

class LogTest {

    private val testTag = "testTag"
    private val testMessage = "testMessage"
    private val testThrowable = mockk<Throwable>()

    @Test
    fun `WHEN v(tag, message, throwable) is called THEN logger logs verbose for proper log level`() {
        val mockedLogger = mockk<Logger>().also { Log.setLogger(it) }
        val testRule = LogTestRule(
            logger = mockedLogger,
            logMethod = Log::v,
            loggerMethod = mockedLogger::verbose
        )
        with(testRule) {
            `assert that logger method is called for`(level = Log.Level.ALL)
            `assert that logger method is called for`(level = Log.Level.VERBOSE)
            `assert that non of logger method was called for`(level = Log.Level.DEBUG)
            `assert that non of logger method was called for`(level = Log.Level.INFO)
            `assert that non of logger method was called for`(level = Log.Level.WARNING)
            `assert that non of logger method was called for`(level = Log.Level.ERROR)
            `assert that non of logger method was called for`(level = Log.Level.ASSERT)
            `assert that non of logger method was called for`(level = Log.Level.NONE)
        }
    }

    @Test
    fun `WHEN d(tag, message, throwable) is called THEN logger logs debug for proper log level`() {
        val mockedLogger = mockk<Logger>().also { Log.setLogger(it) }
        val logTestRule = LogTestRule(
            logger = mockedLogger,
            logMethod = Log::d,
            loggerMethod = mockedLogger::debug
        )
        with(logTestRule) {
            `assert that logger method is called for`(level = Log.Level.ALL)
            `assert that logger method is called for`(level = Log.Level.VERBOSE)
            `assert that logger method is called for`(level = Log.Level.DEBUG)
            `assert that non of logger method was called for`(level = Log.Level.INFO)
            `assert that non of logger method was called for`(level = Log.Level.WARNING)
            `assert that non of logger method was called for`(level = Log.Level.ERROR)
            `assert that non of logger method was called for`(level = Log.Level.ASSERT)
            `assert that non of logger method was called for`(level = Log.Level.NONE)
        }
    }

    @Test
    fun `WHEN i(tag, message, throwable) is called THEN logger logs info for proper log level`() {
        val mockedLogger = mockk<Logger>().also { Log.setLogger(it) }
        val logTestRule = LogTestRule(
            logger = mockedLogger,
            logMethod = Log::i,
            loggerMethod = mockedLogger::info
        )
        with(logTestRule) {
            `assert that logger method is called for`(level = Log.Level.ALL)
            `assert that logger method is called for`(level = Log.Level.VERBOSE)
            `assert that logger method is called for`(level = Log.Level.DEBUG)
            `assert that logger method is called for`(level = Log.Level.INFO)
            `assert that non of logger method was called for`(level = Log.Level.WARNING)
            `assert that non of logger method was called for`(level = Log.Level.ERROR)
            `assert that non of logger method was called for`(level = Log.Level.ASSERT)
            `assert that non of logger method was called for`(level = Log.Level.NONE)
        }
    }

    @Test
    fun `WHEN w(tag, message, throwable) is called THEN logger logs warning for proper log level`() {
        val mockedLogger = mockk<Logger>().also { Log.setLogger(it) }
        val logTestRule = LogTestRule(
            logger = mockedLogger,
            logMethod = Log::w,
            loggerMethod = mockedLogger::warning
        )
        with(logTestRule) {
            `assert that logger method is called for`(level = Log.Level.ALL)
            `assert that logger method is called for`(level = Log.Level.VERBOSE)
            `assert that logger method is called for`(level = Log.Level.DEBUG)
            `assert that logger method is called for`(level = Log.Level.INFO)
            `assert that logger method is called for`(level = Log.Level.WARNING)
            `assert that non of logger method was called for`(level = Log.Level.ERROR)
            `assert that non of logger method was called for`(level = Log.Level.ASSERT)
            `assert that non of logger method was called for`(level = Log.Level.NONE)
        }
    }

    @Test
    fun `WHEN e(tag, message, throwable) is called THEN logger logs error for proper log level`() {
        val mockedLogger = mockk<Logger>().also { Log.setLogger(it) }
        val logTestRule = LogTestRule(
            logger = mockedLogger,
            logMethod = Log::e,
            loggerMethod = mockedLogger::error
        )
        with(logTestRule) {
            `assert that logger method is called for`(level = Log.Level.ALL)
            `assert that logger method is called for`(level = Log.Level.VERBOSE)
            `assert that logger method is called for`(level = Log.Level.DEBUG)
            `assert that logger method is called for`(level = Log.Level.INFO)
            `assert that logger method is called for`(level = Log.Level.WARNING)
            `assert that logger method is called for`(level = Log.Level.ERROR)
            `assert that non of logger method was called for`(level = Log.Level.ASSERT)
            `assert that non of logger method was called for`(level = Log.Level.NONE)
        }
    }

    @Test
    fun `WHEN wtf(tag, message, throwable) is called THEN logger logs wtf for proper log level`() {
        val mockedLogger = mockk<Logger>().also { Log.setLogger(it) }
        val logTestRule = LogTestRule(
            logger = mockedLogger,
            logMethod = Log::wtf,
            loggerMethod = mockedLogger::wtf
        )
        with(logTestRule) {
            `assert that logger method is called for`(level = Log.Level.ALL)
            `assert that logger method is called for`(level = Log.Level.VERBOSE)
            `assert that logger method is called for`(level = Log.Level.DEBUG)
            `assert that logger method is called for`(level = Log.Level.INFO)
            `assert that logger method is called for`(level = Log.Level.WARNING)
            `assert that logger method is called for`(level = Log.Level.ERROR)
            `assert that logger method is called for`(level = Log.Level.ASSERT)
            `assert that non of logger method was called for`(level = Log.Level.NONE)
        }
    }

    private data class LogTestRule(
        val logger: Logger,
        val logMethod: (String, String, Throwable?) -> Unit,
        val loggerMethod: (String, String, Throwable?) -> Unit,
    )


    private fun LogTestRule.`assert that logger method is called for`(level: Log.Level) {
        Log.setLevel(level)
        clearMocks(logger)
        every { loggerMethod(testTag, testMessage, testThrowable) } just runs

        logMethod(testTag, testMessage, testThrowable)

        verifyCalledOnes { loggerMethod(testTag, testMessage, testThrowable) }
    }

    private fun LogTestRule.`assert that non of logger method was called for`(
        level: Log.Level
    ) {
        Log.setLevel(level)
        clearMocks(logger)

        logMethod(testTag, testMessage, testThrowable)

        verify { logger wasNot called }
    }
}
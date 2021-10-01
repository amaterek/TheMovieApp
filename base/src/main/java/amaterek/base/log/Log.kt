package amaterek.base.log

import amaterek.base.log.logger.DefaultLogger
import amaterek.base.log.logger.Logger

// For compatibility with Android's Log
object Log {

    enum class Level(val value: Int) {
        ALL(0),
        VERBOSE(1),
        DEBUG(2),
        INFO(3),
        WARNING(4),
        ERROR(5),
        ASSERT(6),
        NONE(7),
    }

    fun setLogger(logger: Logger) {
        Log.logger = logger
    }

    fun setLevel(level: Log.Level) {
        Log.level = level
    }

    fun v(tag: String, message: String, throwable: Throwable? = null) {
        if (level.value <= Level.VERBOSE.value) logger.verbose(tag, message, throwable)
    }

    fun d(tag: String, message: String, throwable: Throwable? = null) {
        if (level.value <= Level.DEBUG.value) logger.debug(tag, message, throwable)
    }

    fun i(tag: String, message: String, throwable: Throwable? = null) {
        if (level.value <= Level.INFO.value) logger.info(tag, message, throwable)
    }

    fun w(tag: String, message: String = "", throwable: Throwable? = null) {
        if (level.value <= Level.WARNING.value) logger.warning(tag, message, throwable)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (level.value <= Level.ERROR.value) logger.error(tag, message, throwable)
    }

    fun wtf(tag: String, message: String = "", throwable: Throwable? = null) {
        if (level.value <= Level.ASSERT.value) logger.wtf(tag, message, throwable)
    }

    private var logger: Logger = DefaultLogger()
    private var level: Level = Level.ALL
}
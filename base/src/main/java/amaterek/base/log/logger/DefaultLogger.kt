package amaterek.base.log.logger

class DefaultLogger : Logger {

    override fun verbose(tag: String, message: String, throwable: Throwable?) {
        val throwableMessage = throwable?.let { " throwable=$throwable" } ?: ""
        println("VERBOSE: $tag: $message$throwableMessage")
    }

    override fun debug(tag: String, message: String, throwable: Throwable?) {
        val throwableMessage = throwable?.let { " throwable=$throwable" } ?: ""
        println("DEBUG: $tag: $message$throwableMessage")
    }

    override fun info(tag: String, message: String, throwable: Throwable?) {
        val throwableMessage = throwable?.let { " throwable=$throwable" } ?: ""
        println("INFO: $tag: $message$throwableMessage")
    }

    override fun warning(tag: String, message: String, throwable: Throwable?) {
        val throwableMessage = throwable?.let { " throwable=$throwable" } ?: ""
        println("WARN: $tag: $message$throwableMessage")
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        val throwableMessage = throwable?.let { " throwable=$throwable" } ?: ""
        println("ERROR: $tag: $message$throwableMessage")
    }

    override fun wtf(tag: String, message: String, throwable: Throwable?) {
        val throwableMessage = throwable?.let { " throwable=$throwable" } ?: ""
        println("WTF: $tag: $message$throwableMessage")
    }
}
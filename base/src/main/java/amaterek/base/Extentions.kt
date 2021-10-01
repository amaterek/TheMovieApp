package amaterek.base

fun Any.toHexString() = String.format("%08X", hashCode())

fun Any.logTag(): String = "${javaClass.simpleName}@${toHexString()}"
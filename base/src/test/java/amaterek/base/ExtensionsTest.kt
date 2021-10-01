package amaterek.base

import org.junit.Test
import kotlin.test.assertEquals

class ExtensionsTest {

    private class TestClass {
        var testHashCode: Int = 0

        override fun hashCode(): Int = testHashCode
    }

    @Test
    fun `toHexString formats object's hash`() {
        val testObject = TestClass()
        mapOf(
            0x0 to "00000000",
            0x12345 to "00012345",
            -1 to "FFFFFFFF",
            Int.MAX_VALUE to "7FFFFFFF",
            Int.MIN_VALUE to "80000000",
        ).forEach { (hashCode, expected) ->
            testObject.testHashCode = hashCode
            assertEquals(expected, testObject.toHexString())
        }
    }

    @Test
    fun `logTag formats object's hash`() {
        val testObject = TestClass().apply { testHashCode = 0x12345678 }
        assertEquals("TestClass@12345678", testObject.logTag())
    }
}
package amaterek.base.test

import io.mockk.MockKVerificationScope
import io.mockk.coVerify
import io.mockk.verify

fun verifyCalledOnes(verifyBlock: MockKVerificationScope.() -> Unit) {
    verify(exactly = 1, verifyBlock = verifyBlock)
}

fun coVerifyCalledOnes(verifyBlock: suspend MockKVerificationScope.() -> Unit) {
    coVerify(exactly = 1, verifyBlock = verifyBlock)
}

fun verifyNotCalled(verifyBlock: MockKVerificationScope.() -> Unit) {
    verify(exactly = 0, verifyBlock = verifyBlock)
}

fun coVerifyNotCalled(verifyBlock: suspend MockKVerificationScope.() -> Unit) {
    coVerify(exactly = 0, verifyBlock = verifyBlock)
}
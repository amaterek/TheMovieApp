plugins {
    id("common.library")
}

dependencies {
    api(Libs.Kotlin.test)
    api(Libs.Kotlin.testCoroutines)
    api(Libs.Test.junit)
    api(Libs.Test.mockk)
    api(Libs.Test.turbine)
}
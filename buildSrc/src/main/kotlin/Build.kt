object Build {
    const val androidTools = "31.0.0"
    const val minSdk = 23
    const val compileSdk = 31
    const val targetSdk = 30 // 30 because of error while compiling androidTest

    val commonCompileArgs = listOf(
//        "-Werror",
        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xopt-in=kotlinx.coroutines.FlowPreview",
        "-Xopt-in=kotlin.time.ExperimentalTime",
        "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        "-Xallow-unstable-dependencies",
    )
}
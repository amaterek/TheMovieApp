object Build {
    const val androidTools = "31.0.0"
    const val minSdk = 23
    const val compileSdk = 31
    const val targetSdk = 30 // 30 because of error while compiling androidTest

    val commonCompileArgs = listOf(
//        "-Werror",
        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
        "-Xuse-experimental=kotlin.time.ExperimentalTime",
        "-Xuse-experimental=androidx.compose.foundation.ExperimentalFoundationApi",
        "-Xallow-unstable-dependencies",
    )
}
plugins {
    id("common.android-library")
}

android {
    buildFeatures {
        compose = false
    }
}

dependencies {
    api(project(":base"))

    api(Libs.AndroidX.activity)
    api(Libs.AndroidX.appCompat)
    api(Libs.AndroidX.core)
    api(Libs.AndroidX.Lifecycle.common)
    api(Libs.AndroidX.Lifecycle.viewModel)

    testImplementation(project(":base-test-android"))
}

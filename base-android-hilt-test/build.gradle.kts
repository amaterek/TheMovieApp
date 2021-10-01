plugins {
    id("common.android-library")
}

android {
    buildFeatures {
        compose = false
    }
}

dependencies {
    api(project(":base-android"))
    api(Libs.AndroidX.Test.runner)
    api(Libs.Hilt.androidTest)

    implementation(Libs.AndroidX.appCompat)
    androidTestImplementation(Libs.AndroidX.Compose.runtime)
}

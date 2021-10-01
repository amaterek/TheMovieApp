plugins {
    id("common.android-library")
}

android {
    buildFeatures {
        compose = false
    }
}

dependencies {
    api(project(":base-test"))

    implementation(Libs.AndroidX.Lifecycle.viewModel)
}

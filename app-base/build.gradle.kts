plugins {
    id("common.android-library")
}

dependencies {
    implementation(project(":base"))
    implementation(project(":domain"))

    api(Libs.AndroidX.hiltNavigationCompose)
    api(Libs.AndroidX.Compose.foundation)
    api(Libs.AndroidX.Compose.runtime)
    api(Libs.AndroidX.Compose.ui)
    api(Libs.AndroidX.Compose.uiTooling)
    api(Libs.AndroidX.Lifecycle.composeViewModel)
    api(Libs.AndroidX.Navigation.compose)

    testImplementation(project(":base-test"))
}

plugins {
    id("common.android-library")
}

dependencies {
    implementation(project(":base-android"))
    implementation(project(":app-base"))
    implementation(project(":app-theme"))

    debugImplementation(Libs.AndroidX.Test.Compose.manifest)
    debugImplementation(project(":base-android-hilt-test"))

    testImplementation(project(":base-test-android"))

    androidTestImplementation(Libs.AndroidX.Test.Compose.junit4)
}
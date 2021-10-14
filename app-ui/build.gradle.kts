plugins {
    id("common.android-library")
}

dependencies {
    implementation(project(":base-android"))
    implementation(project(":domain"))
    implementation(project(":app-base"))
    implementation(project(":app-theme"))
    implementation(Libs.ThirdParty.skydovesComoseCoil)
    implementation(Libs.ThirdParty.gowthamComposeRatingbar)
    implementation(Libs.ThirdParty.oneboneComposeToolbar)

    debugImplementation(Libs.AndroidX.Test.Compose.manifest)
    debugImplementation(project(":base-android-hilt-test"))

    testImplementation(project(":base-test-android"))

    androidTestImplementation(project(":fake-movie-repository"))
    androidTestImplementation(Libs.AndroidX.Test.Compose.junit4)
}
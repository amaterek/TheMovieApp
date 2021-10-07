plugins {
    id("common.library")
    kotlin("kapt")
}

dependencies {
    implementation(project(":base"))
    implementation(project(":domain"))

    implementation(Libs.Hilt.dagger)

    kapt(Libs.Hilt.kapt)

    testImplementation(project(":base-test"))
}

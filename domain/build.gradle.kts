plugins {
    id("common.library")
    kotlin("kapt")
}

dependencies {
    testApi(project(":base-test"))

    implementation(Libs.Hilt.dagger)

    kapt(Libs.Hilt.kapt)
}

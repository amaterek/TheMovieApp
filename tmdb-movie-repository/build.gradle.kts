plugins {
    id("common.android-library")
}

android {
    buildFeatures {
        compose = false
    }
}

dependencies {
    implementation(project(":base-android"))
    implementation(project(":domain"))

    implementation(Libs.AndroidX.Room.core)
    api(Libs.AndroidX.Room.runtime)
    implementation(Libs.Moshi.core)
    implementation(Libs.OkHttp3.logging)
    api(Libs.Retrofit.core)
    implementation(Libs.Retrofit.converterMoshi)

    kapt(Libs.AndroidX.Room.kapt)
    kapt(Libs.Moshi.kapt)

    testImplementation(project(":base-test"))
}

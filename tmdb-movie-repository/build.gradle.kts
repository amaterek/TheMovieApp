import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("common.android-library")
}

android {
    buildTypes {
        all {
            buildConfigField(
                type = "String",
                name = "TMDB_BASE_URL",
                value = "\"https://api.themoviedb.org/3/\""
            )
            buildConfigField(
                type = "String",
                name = "TMDB_BASE_IMAGE_URL",
                value = "\"https://image.tmdb.org/t/p/\""
            )
            buildConfigField(
                type = "String",
                name = "TMDB_API_KEY",
                value = "\"${gradleLocalProperties(rootDir).getProperty("tmdb.api_key") ?: ""}\""
            )

        }
    }

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

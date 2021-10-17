import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("common.android-application")
}

android {
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

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
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug") // temporary solution
        }
    }
}

dependencies {
    implementation(project(":base-android"))
    implementation(project(":app-base"))
    implementation(project(":app-theme"))
    implementation(project(":app-ui"))
    implementation(project(":domain"))
    implementation(project(":tmdb-movie-repository"))
    implementation(project(":fake-movie-repository"))
}

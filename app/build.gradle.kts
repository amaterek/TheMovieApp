plugins {
    id("common.android-application")
}

android {
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

    buildTypes {
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

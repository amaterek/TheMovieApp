plugins {
    id("common.android-application")
}

android {
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug") // temporary solution
        }
    }
}

dependencies {
    implementation(project(":base-android"))
    implementation(project(":app-base"))
    implementation(project(":app-theme"))
    implementation(project(":app-ui"))
}

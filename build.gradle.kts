// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Libs.Gradle.plugin)
        classpath(Libs.Kotlin.plugin)
        classpath(Libs.Hilt.plugin)
    }
}

allprojects {

    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io") // temporary for com.github.a914-gowtham
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

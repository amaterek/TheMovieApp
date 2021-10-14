object Libs {

    object Gradle {
        const val plugin =
            "com.android.tools.build:gradle:${Versions.gradle}"
    }

    object Kotlin {

        const val plugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

        const val test =
            "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"

        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

        const val testCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object AndroidX {

        const val core =
            "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"

        const val appCompat =
            "androidx.appcompat:appcompat:${Versions.AndroidX.appcompat}"

        const val activity =
            "androidx.activity:activity-ktx:${Versions.AndroidX.activity}"

        const val activityCompose =
            "androidx.activity:activity-compose:${Versions.AndroidX.activity}"

        const val fragment =
            "androidx.fragment:fragment-ktx:${Versions.AndroidX.fragment}"

        const val recyclerView =
            "androidx.recyclerview:recyclerview:${Versions.AndroidX.recyclerView}"

        const val constraintLayout =
            "androidx.recyclerview:recyclerview:${Versions.AndroidX.recyclerView}"

        const val swiperefreshlayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.AndroidX.swiperefreshlayout}"

        const val hiltNavigationCompose =
            "androidx.hilt:hilt-navigation-compose:${Versions.AndroidX.hiltCompose}"

        object Compose {
            const val runtime =
                "androidx.compose.runtime:runtime:${Versions.AndroidX.compose}"

            const val ui =
                "androidx.compose.ui:ui:${Versions.AndroidX.compose}"

            const val uiTooling =
                "androidx.compose.ui:ui-tooling:${Versions.AndroidX.compose}"

            const val foundation =
                "androidx.compose.foundation:foundation:${Versions.AndroidX.compose}"

            const val material =
                "androidx.compose.material:material:${Versions.AndroidX.compose}"

            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:${Versions.AndroidX.compose}"
        }

        object Navigation {
            const val fragment =
                "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.navigation}"

            const val ui =
                "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.navigation}"

            const val compose =
                "androidx.navigation:navigation-compose:${Versions.AndroidX.navigation}"
        }

        object Room {
            // https://developer.android.com/jetpack/androidx/releases/room

            const val runtime =
                "androidx.room:room-runtime:${Versions.AndroidX.room}"

            const val core =
                "androidx.room:room-ktx:${Versions.AndroidX.room}"

            const val kapt =
                "androidx.room:room-compiler:${Versions.AndroidX.room}"
        }

        object Lifecycle {
            // https://developer.android.com/jetpack/androidx/releases/lifecycle

            const val common =
                "androidx.lifecycle:lifecycle-common-java8:${Versions.AndroidX.lifecycle}"

            const val kapt =
                "androidx.lifecycle:lifecycle-compiler:${Versions.AndroidX.lifecycle}"

            const val runtime =
                "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifecycle}"

            const val livedata =
                "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"

            const val viewModel =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}"

            const val composeViewModel =
                "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.AndroidX.lifecycle}"
        }

        object Test {

            const val core =
                "androidx.test:core-ktx:${Versions.AndroidX.testCore}"

            const val junit =
                "androidx.test.ext:junit-ktx:${Versions.AndroidX.testJUnit}"

            const val rules =
                "androidx.test:rules:${Versions.AndroidX.testRules}"

            const val runner =
                "androidx.test:runner:${Versions.AndroidX.testRunner}"

            const val espresso =
                "androidx.test.espresso:espresso-core:${Versions.AndroidX.testEspresso}"

            const val uiAutomator =
                "androidx.test.uiautomator:uiautomator:${Versions.AndroidX.testUiAutomator}"

            const val orchestrator =
                "androidx.test:orchestrator:${Versions.AndroidX.testOrchestrator}"

            object Compose {

                const val junit4 =
                    "androidx.compose.ui:ui-test-junit4:${Versions.AndroidX.compose}"

                const val manifest =
                    "androidx.compose.ui:ui-test-manifest:${Versions.AndroidX.compose}"
            }
        }
    }

    object Hilt {

        const val plugin =
            "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

        const val android =
            "com.google.dagger:hilt-android:${Versions.hilt}"

        const val kapt =
            "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

        const val androidTest =
            "com.google.dagger:hilt-android-testing:${Versions.hilt}"

        const val dagger =
            "com.google.dagger:dagger:${Versions.hilt}"
    }

    object Google {

        const val material =
            "com.google.android.material:material:${Versions.material}"
    }

    object OkHttp3 {

        const val core =
            "com.squareup.okhttp3:okhttp:${Versions.okHttp3}"

        const val tls =
            "com.squareup.okhttp3:okhttp-tls:${Versions.okHttp3}"

        const val logging =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp3}"
    }

    object Retrofit {

        const val core =
            "com.squareup.retrofit2:retrofit:${Versions.retrofit}"

        const val converterGson =
            "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

        const val converterMoshi =
            "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    }

    object Gson {

        const val core =
            "com.google.code.gson:gson:${Versions.gson}"
    }

    object Moshi {

        const val core =
            "com.squareup.moshi:moshi:${Versions.moshi}"

        const val kapt =
            "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    }

    object Coil {
        const val compose =
            "io.coil-kt:coil-compose:${Versions.coil}"
    }

    object Test {

        const val junit =
            "junit:junit:${Versions.junit4}"

        const val mockk =
            "io.mockk:mockk:${Versions.mockk}"

        const val turbine =
            "app.cash.turbine:turbine:${Versions.turbine}"
    }

    object ThirdParty {
        const val skydovesComoseCoil =
            "com.github.skydoves:landscapist-coil:${Versions.skydovesComose}"

        const val gowthamComposeRatingbar =
            "com.github.a914-gowtham:compose-ratingbar:${Versions.gowthamComposeRatingbar}"

        const val oneboneComposeToolbar =
            "me.onebone:toolbar-compose:${Versions.oneboneComposeToolbar}"
    }
}

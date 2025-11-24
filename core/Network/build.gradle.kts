plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kmp.targets)
}

kotlin {
    sourceSets {
        commonMain.dependencies {

            // Koin (DI)
            api(libs.bundles.di)

            // Ktor Core + JSON Serialization
            api(libs.ktor.client.core)
            api(libs.ktor.client.contentneg)
            api(libs.ktor.serialization.kotlinx)
            api(libs.kotlinx.serialization.json)

            // Logging (shared API — engine-specific logging happens on platform)
            api(libs.ktor.client.logging)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.cio)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

android {
    namespace = "me.inassar.core.network"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

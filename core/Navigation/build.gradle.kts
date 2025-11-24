plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kmp.targets)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.compose.navigation)
            api(libs.koin.core)
            api(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "me.inassar.core.navigation"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
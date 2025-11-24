plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kmp.targets)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            api(libs.bundles.compose.ui)
            api(projects.core.navigation)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "me.inassar.core.ui"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
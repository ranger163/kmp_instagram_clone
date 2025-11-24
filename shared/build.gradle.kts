plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kmp.targets)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.kotlinx)
            api(libs.kermit)
            api(libs.bundles.ui.media)
            api(libs.bundles.di)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "me.inassar.navigation.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

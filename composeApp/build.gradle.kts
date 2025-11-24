import convention.KmpMultiplatformConventionPlugin
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.atomicfu)
    alias(libs.plugins.kmp.targets)
}

kotlin {

    KmpMultiplatformConventionPlugin()

    targets.withType<KotlinNativeTarget>()
        .matching { it.konanTarget.family.isAppleFamily }.configureEach {
            binaries.framework {
                baseName = "ComposeApp"
                isStatic = true

                export(projects.shared)
                export(projects.core.cache)
                export(projects.core.network)
                export(projects.core.navigation)
                export(projects.core.ui)

                export(projects.feature.auth)
                export(projects.feature.feed)
                export(projects.feature.explore)
                export(projects.feature.newPost)
                export(projects.feature.likes)
                export(projects.feature.profile)
            }
        }

    sourceSets {

        commonMain.dependencies {
            implementation(libs.bundles.compose.ui)
            implementation(libs.bundles.androidx.lifecycle)
            implementation(libs.kotlinx.atomicfu)

            api(libs.bundles.di)

            api(projects.shared)
            api(projects.feature.auth)
            api(projects.feature.feed)
            api(projects.feature.explore)
            api(projects.feature.newPost)
            api(projects.feature.likes)
            api(projects.feature.profile)

            api(projects.core.ui)
            api(projects.core.navigation)
            api(projects.core.network)
            api(projects.core.cache)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "me.inassar.kmp_instagram_clone.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "me.inassar.kmp_instagram_clone"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "me.inassar.kmp_instagram_clone.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "me.inassar.kmp_instagram_clone"
            packageVersion = "1.0.0"
        }
    }
}

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Cache"
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        androidMain.dependencies { api(libs.sqldelight.android.driver) }
        commonMain.dependencies {
            api(libs.sqldelight.runtime)
            api(libs.sqldelight.coroutines.extensions)
            api(libs.kotlinx.serialization.json)
            api(libs.bundles.database.client)
            api(projects.shared)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies { api(libs.sqldelight.ios.driver) }
        jvmMain.dependencies { api(libs.sqldelight.jvm.driver) }
        jsMain.dependencies {
            api(libs.sqldelight.web.driver)
        }

        wasmJsMain.dependencies {
            api(libs.sqldelight.web.driver)
        }

    }
}

android {
    namespace = "me.inassar.core.cache"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

sqldelight {
    databases {
        create(name = "AppDatabase") {
            packageName.set("me.inassar.core.cache.db")
        }
    }
    // for native platforms (iOS, etc.) – replaces manual linkerOpts("-lsqlite3")
    linkSqlite.set(true)
}

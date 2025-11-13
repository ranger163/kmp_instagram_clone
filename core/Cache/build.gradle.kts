import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.serialization.json)
                api(libs.bundles.database.client)
                api(projects.shared)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        // No js/wasm source sets in this module → Room in commonMain is safe
    }
}

dependencies {
    // Room KSP per target
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspJvm", libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "me.inassar.core.cache"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

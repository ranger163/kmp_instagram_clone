plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kmp.targets)
}

kotlin {

    sourceSets {
        androidMain.dependencies { api(libs.sqldelight.android.driver) }
        commonMain.dependencies {
            api(libs.sqldelight.runtime)
            api(libs.sqldelight.coroutines.extensions)
            api(libs.kotlinx.serialization.json)
            api(projects.shared)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies { api(libs.sqldelight.ios.driver) }
        jvmMain.dependencies { api(libs.sqldelight.jvm.driver) }
        jsMain.dependencies {
            implementation(libs.sqldelight.web.driver)
            implementation(
                npm(
                    "@cashapp/sqldelight-sqljs-worker",
                    libs.versions.sqlDelight.get()
                )
            )
        }

        wasmJsMain.dependencies {
            api(libs.sqldelight.web.driver)
            implementation(
                npm(
                    "@cashapp/sqldelight-sqljs-worker",
                    libs.versions.sqlDelight.get()
                )
            )
        }

    }
}

android {
    namespace = "me.inassar.core.cache"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

sqldelight {
    databases {
        create(name = "AppDatabase").apply {
            packageName.set("me.inassar.core.cache.db")
        }
    }
    // for native platforms (iOS, etc.) – replaces manual linkerOpts("-lsqlite3")
    linkSqlite.set(true)
}

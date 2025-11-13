rootProject.name = "kmp_instagram_clone"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":shared")
include(":composeApp")
include(":feature:Auth")
include(":feature:Feed")
include(":feature:Explore")
include(":feature:NewPost")
include(":feature:Likes")
include(":feature:Profile")
include(":core:UI")
include(":core:Navigation")
include(":core:Network")
include(":core:Cache")
include(":server")

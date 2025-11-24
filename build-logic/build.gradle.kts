plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.build.gradle)
}

gradlePlugin {
    plugins {
        create("kmpMultiplatformConvention").apply {
            id = "kmp-targets"
            implementationClass = "convention.KmpMultiplatformConventionPlugin"
        }
    }
}

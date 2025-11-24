package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * A Gradle convention plugin that standardizes the Kotlin Multiplatform configuration
 * across all modules in a multi-module KMP project.
 *
 * This plugin ensures that every module shares a unified and maintainable
 * multiplatform setup—reducing build script duplication and making global
 * configuration changes easier to manage from a single location.
 *
 * ## Responsibilities
 *
 * The plugin performs the following actions:
 *
 * ### 1. Applies the Kotlin Multiplatform plugin
 * Ensures the module is configured as a KMP module:
 * ```
 * id("org.jetbrains.kotlin.multiplatform")
 * ```
 *
 * ### 2. Configures common targets for all KMP modules:
 * - **Android** target using JVM 17
 * - **iOS** targets: `iosX64`, `iosArm64`, `iosSimulatorArm64`
 * - **JVM** target for desktop/server workloads
 * - **JavaScript** (browser) target
 * - **WasmJS** (browser) target with experimental opt-in
 *
 * These targets allow each KMP module to be used by mobile, web, desktop, or server layers
 * in a fully multiplatform architecture.
 *
 * ### 3. Disables JS and Wasm test tasks
 * Browser test tasks are disabled to improve build performance in modules
 * where JavaScript/Wasm testing is not needed.
 *
 * ### 4. Provides a consistent foundation for all KMP modules
 * Modules applying this plugin can still customize their own `sourceSets`,
 * but the shared targets and baseline configuration are defined centrally here.
 *
 * ## Usage
 *
 * In any module's `build.gradle.kts`, apply the plugin:
 * ```
 * plugins {
 *     id("convention.kmp-multiplatform")
 * }
 * ```
 *
 * The plugin automatically applies all targets and KMP essentials.
 *
 * ## Motivation
 *
 * Without this plugin, each module would require duplicating a large
 * `kotlin { ... }` block—including targets, compiler settings, JS/Wasm configuration,
 * and iOS setup.
 *
 * Centralizing this logic:
 * - Reduces copy–paste errors
 * - Ensures team-wide consistency
 * - Speeds up adding new modules
 * - Makes upgrading KMP/Compose/Toolchain easier
 *
 * ## Extension Points
 *
 * Modules can still:
 * - Add dependencies inside `sourceSets`
 * - Create new platform-specific source sets
 * - Override or extend configurations bound by this plugin
 *
 * ## When to modify this plugin
 *
 * Update this plugin when:
 * - Adding/removing KMP targets
 * - Upgrading JVM/Android toolchain versions
 * - Changing KMP shared compiler flags
 * - Introducing new conventions for the entire codebase
 *
 * ## Intended audience
 *
 * This plugin is meant for Kotlin Multiplatform architects and teams building
 * large, modularized KMP applications where consistent multiplatform configuration
 * is essential.
 */
@OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
class KmpMultiplatformConventionPlugin : Plugin<Project> {

    /**
     * Applies the convention plugin to the given [Project].
     *
     * The function performs:
     * - Application of the `org.jetbrains.kotlin.multiplatform` plugin
     * - Configuration of shared multiplatform targets
     * - Consistent JVM target setup for Android builds
     * - Unified JS and Wasm browser configuration
     * - A placeholder `sourceSets {}` block that modules may override
     */
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    override fun apply(target: Project) = with(target) {

        // Ensure the Kotlin Multiplatform plugin is applied
        pluginManager.apply("org.jetbrains.kotlin.multiplatform")

        // Configure the shared multiplatform extension
        extensions.configure<KotlinMultiplatformExtension> {

            androidTarget {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }

            iosX64()
            iosArm64()
            iosSimulatorArm64()

            jvm()

            js {
                browser {
                    testTask { enabled = false }
                }
                binaries.executable()
            }

            wasmJs {
                browser {
                    testTask { enabled = false }
                }
                binaries.executable()
            }

            // Modules may define their own dependencies or structure
            sourceSets { }
        }
    }
}

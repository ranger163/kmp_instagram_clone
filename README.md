# Kotlin Multiplatform Instagram Clone

![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-blueviolet)
![Ktor](https://img.shields.io/badge/Backend-Ktor-orange)
![Koin](https://img.shields.io/badge/DI-Koin-41B883)
![License](https://img.shields.io/badge/License-MIT-green)

A reference Kotlin Multiplatform (KMP) project that mirrors an Instagram-style experience across Android, iOS, Desktop, Web, and Wasm while also shipping a Ktor server. The codebase uses vertical, feature-first modularization with a strict Clean Architecture split between navigation/UI scaffolding, shared platform services, and feature-owned data-domain-presentation layers.

## Architecture

The app follows a layered, feature-based Clean Architecture:

- **ComposeApp** – entry point per platform; bootstraps Koin and hosts the shared scaffold/nav graph. Each platform simply calls `App()` while providing platform DI hooks (e.g., Android `KmpApp`).【F:composeApp/src/commonMain/kotlin/me/inassar/kmp_instagram_clone/App.kt†L4-L25】【F:composeApp/src/androidMain/kotlin/me/inassar/kmp_instagram_clone/App.kt†L1-L11】
- **Core** – cross-feature capabilities: typed navigation, UI scaffold/top & bottom bars, Ktor client, and SQLDelight cache abstractions. Features depend only on these modules, never the other way around.【F:core/Navigation/src/commonMain/kotlin/me/inassar/core/navigation/FeatureEntry.kt†L6-L18】【F:core/UI/src/commonMain/kotlin/me/inassar/core/ui/scafold/AppScaffold.kt†L16-L47】【F:core/Network/src/commonMain/kotlin/me/inassar/core/network/NetworkModule.kt†L6-L23】【F:core/Cache/src/commonMain/kotlin/me/inassar/core/cache/di/cacheModule.kt†L1-L14】
- **Shared** – pure KMP utilities: platform capabilities, dispatcher providers, logging, constants, and DI glue that bridges platform specifics (Android/iOS/JS/Wasm/JVM).【F:shared/src/commonMain/kotlin/me/inassar/shared/di/sharedModule.kt†L1-L9】【F:shared/src/androidMain/kotlin/me/inassar/shared/Modules.android.kt†L9-L18】
- **Feature modules** – vertical slices (Auth, Feed, Explore, NewPost, Likes, Profile) each defining navigation entry points, UI config providers, presentation state, domain models, data sources, and DI bindings. Example: Feed owns its repository, cache, remote API, mappers, and ViewModel while exposing only navigation/UI contracts.【F:feature/Feed/src/commonMain/kotlin/me/inassar/feature/feed/FeedEntry.kt†L4-L62】【F:feature/Feed/src/commonMain/kotlin/me/inassar/feature/feed/di/FeedModules.kt†L1-L20】
- **Server** – lightweight Ktor server that reuses the `shared` module for cross-platform constants and greeting logic.【F:server/src/main/kotlin/me/inassar/server/Application.kt†L4-L19】

### Clean Architecture data flow

1. **UI** -> triggers feature-specific ViewModel actions (e.g., `FeedEvent`).【F:feature/Feed/src/commonMain/kotlin/me/inassar/feature/feed/presentation/ui/FeedScreen.kt†L24-L49】
2. **Presentation** -> ViewModel calls **Domain Repository** abstractions (`FeedRepository`).【F:feature/Feed/src/commonMain/kotlin/me/inassar/feature/feed/presentation/manipulator/FeedViewmodel.kt†L16-L47】
3. **Domain** -> delegates to **Data** implementations that orchestrate cache + remote and map DTOs to domain models.【F:feature/Feed/src/commonMain/kotlin/me/inassar/feature/feed/data/repository/FeedRepositoryImpl.kt†L1-L42】
4. **Data** -> uses **Core Network** (`NetworkClient`) and **Core Cache** (SQLDelight queries) which are provided via DI.【F:feature/Feed/src/commonMain/kotlin/me/inassar/feature/feed/data/remote/source/FeedRemoteApiImpl.kt†L1-L11】【F:feature/Feed/src/commonMain/kotlin/me/inassar/feature/feed/data/cache/source/FeedCacheImpl.kt†L1-L19】
5. Responses flow back as immutable domain models -> UI mappers -> rendered composables.【F:feature/Feed/src/commonMain/kotlin/me/inassar/feature/feed/presentation/mapper/UiFeedMapper.kt†L1-L6】

### Navigation and scaffold

- Typed routes (`AppRoute`) avoid stringly-typed navigation and power compile-time safety across features.【F:core/Navigation/src/commonMain/kotlin/me/inassar/core/navigation/Routes.kt†L1-L22】
- Each feature implements `FeatureEntry` to register its destinations and decode back stack entries into typed routes.【F:core/Navigation/src/commonMain/kotlin/me/inassar/core/navigation/FeatureEntry.kt†L6-L18】
- The shared `AppScaffold` wires top and bottom bars based on feature-provided `UiConfigProvider`, then renders the aggregated NavHost.【F:core/UI/src/commonMain/kotlin/me/inassar/core/ui/scafold/AppScaffold.kt†L1-L47】
- Bottom navigation destinations are centralized via `BottomNavDefaults` to keep tab wiring consistent across features.【F:core/UI/src/commonMain/kotlin/me/inassar/core/ui/components/NavBar.kt†L19-L83】

### Dependency Injection

Koin bootstraps in ComposeApp, combining shared/network/cache modules with feature modules. Platform-specific bindings (dispatchers, SQLite drivers, etc.) live in `shared` and `core:Cache` via expect/actual modules.【F:composeApp/src/commonMain/kotlin/me/inassar/kmp_instagram_clone/di/RegisterModules.kt†L1-L25】【F:core/Cache/src/commonMain/kotlin/me/inassar/core/cache/di/cacheModule.kt†L1-L14】【F:shared/src/commonMain/kotlin/me/inassar/shared/Platform.kt†L1-L6】

### Networking

`core:Network` wraps Ktor `HttpClient` with retry, timeouts, logging, and JSON configuration driven by `NetworkConfig` and exposed through a `NetworkClient` facade registered in DI.【F:core/Network/src/commonMain/kotlin/me/inassar/core/network/NetworkClient.kt†L1-L149】【F:core/Network/src/commonMain/kotlin/me/inassar/core/network/NetworkModule.kt†L6-L23】

## Folder Structure

```
composeApp/
  src/commonMain/...           # Shared Compose entry + DI
core/
  Navigation/                  # Typed routes + NavHost helpers
  UI/                          # Theme, scaffold, app bars, bottom nav
  Network/                     # Ktor client + DI
  Cache/                       # SQLDelight setup + platform drivers
feature/
  Auth|Feed|Explore|NewPost|Likes|Profile/   # Feature slices with UI + DI
shared/                        # Platform utilities (dispatchers, logging)
server/                        # Ktor server reusing shared models
```

## Tech Stack

| Layer | Libraries |
| --- | --- |
| UI | Compose Multiplatform, Material3, Navigation Compose |
| DI | Koin 4.1 |
| Network | Ktor Client (OkHttp/Darwin/CIO/JS), kotlinx-serialization |
| Persistence | SQLDelight (multiplatform drivers) |
| Concurrency | Kotlin Coroutines |
| Backend | Ktor Server + Netty |

## Getting Started

### Prerequisites
- JDK 17+
- Android Studio / IntelliJ with KMP support
- Xcode for iOS builds

### Bootstrap Koin
```kotlin
// composeApp/src/commonMain
fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(allModules)
    }
}
```
`allModules` merges network configuration, shared platform bindings, cache, and feature modules.【F:composeApp/src/commonMain/kotlin/me/inassar/kmp_instagram_clone/di/RegisterModules.kt†L1-L25】 Platform code (e.g., Android `KmpApp`) calls `initKoin` and injects platform context.【F:composeApp/src/androidMain/kotlin/me/inassar/kmp_instagram_clone/App.kt†L1-L11】

### Configure Networking
```kotlin
val appModules = networkModule(
    config = NetworkConfig(
        baseUrl = "https://dummyjson.com",
        enableLogging = true,
        logLevel = LogLevel.BODY,
        defaultHeaders = mapOf(
            HttpHeaders.ContentType to ContentType.Application.Json.toString(),
            HttpHeaders.Accept to ContentType.Application.Json.toString()
        )
    )
) + sharedModule + cacheModule
```
`NetworkClient` exposes `getSafe/postSafe/...` helpers returning `Result<T>` for safe consumption in repositories.【F:core/Network/src/commonMain/kotlin/me/inassar/core/network/NetworkClient.kt†L74-L126】

### Typed Navigation Example
```kotlin
class AuthEntry : FeatureEntry, UiConfigProvider {
    override fun register(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable<AuthRoute> {
            AuthScreen(onNavigateFeed = { navController.navigate(it) })
        }
    }
    override fun tryCreateRoute(entry: NavBackStackEntry): AppRoute? = AuthRoute.takeIf {
        entry.destination.route == "auth"
    }
}
```
Routes implement `AppRoute` (a `@Serializable` sealed interface) and are registered centrally in `AppScaffold`.

### Run Targets
- **Android**: `./gradlew :composeApp:assembleDebug`
- **Desktop (JVM)**: `./gradlew :composeApp:run`
- **Web (JS)**: `./gradlew :composeApp:jsBrowserDevelopmentRun`
- **Web (Wasm)**: `./gradlew :composeApp:wasmJsBrowserDevelopmentRun`
- **iOS**: Open `iosApp` in Xcode or run via IDE
- **Server**: `./gradlew :server:run`

## Benefits
- Single navigation + scaffold system ensures consistent top/bottom bars and type-safe routing across all features.【F:core/UI/src/commonMain/kotlin/me/inassar/core/ui/scafold/AppScaffold.kt†L16-L47】【F:core/UI/src/commonMain/kotlin/me/inassar/core/ui/components/NavBar.kt†L19-L83】
- Shared Ktor client and SQLDelight cache keep data layer cohesive and platform-aware while staying testable via DI.【F:core/Network/src/commonMain/kotlin/me/inassar/core/network/NetworkModule.kt†L6-L23】【F:core/Cache/src/commonMain/kotlin/me/inassar/core/cache/di/cacheModule.kt†L1-L14】
- Feature slices are isolated; only ComposeApp knows about all features through registry wiring, improving scalability and experimentation.【F:composeApp/src/commonMain/kotlin/me/inassar/kmp_instagram_clone/common/FeaturesRegistry.kt†L1-L20】

## Screenshots
Add platform-specific screenshots here:
- Android: `docs/android.png`
- iOS: `docs/ios.png`
- Desktop: `docs/desktop.png`
- Web/Wasm: `docs/web.png`

## License
MIT License

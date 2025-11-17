# Kotlin Multiplatform Instagram Clone

![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-blueviolet)
![Ktor](https://img.shields.io/badge/Backend-Ktor-orange)
![Koin](https://img.shields.io/badge/DI-Koin-41B883)
![License](https://img.shields.io/badge/License-MIT-green)

A reference **Kotlin Multiplatform (KMP)** project recreating an Instagram-style application across:

- **Android**
- **iOS**
- **Desktop (JVM)**
- **Web (JS + Wasm)**
- **Ktor Server Backend**

It showcases **vertical, feature-first modularization** with **strict Clean Architecture**, shared logic, typed navigation, Composable UI, Ktor networking, SQLDelight storage, and fully isolated feature slices.

---

# 📁 Folder Structure

```text
composeApp/
  androidMain/
  iosMain/
  commonMain/
  jvmMain/
  webMain/

core/
  UI/
  Navigation/
  Network/
  Cache/

feature/
  Auth/
  Feed/
  Explore/
  NewPost/
  Likes/
  Profile/

shared/

server/
```

---

# 📦 Dependency Graph

```mermaid
graph TD
    composeApp --> core:UI
    composeApp --> core:Navigation
    composeApp --> core:Network
    composeApp --> core:Cache
    composeApp --> shared
    composeApp --> feature:Auth
    composeApp --> feature:Feed
    composeApp --> feature:Explore
    composeApp --> feature:NewPost
    composeApp --> feature:Likes
    composeApp --> feature:Profile

    feature:Feed --> core:UI
    feature:Feed --> core:Navigation
    feature:Feed --> core:Network
    feature:Feed --> core:Cache

    feature:Auth --> core:Navigation
    feature:Auth --> core:UI

    feature:Explore --> core:UI
    feature:Explore --> core:Navigation

    feature:NewPost --> core:UI
    feature:NewPost --> core:Navigation

    feature:Likes --> core:UI
    feature:Likes --> core:Navigation

    feature:Profile --> core:UI
    feature:Profile --> core:Navigation

    shared --> PlatformUtils[(platform utils)]
    server --> shared
```

---

# 🧭 Navigation Flow

```mermaid
graph LR
    AuthRoute --> FeedRoute
    FeedRoute --> ExploreRoute
    FeedRoute --> NewPostRoute
    FeedRoute --> LikesRoute
    FeedRoute --> ProfileRoute

    ExploreRoute --> FeedRoute
    LikesRoute --> FeedRoute
    NewPostRoute --> FeedRoute
    ProfileRoute --> FeedRoute
```

---

# 🔧 Gradle Dependency Flow

```mermaid
graph TD
    composeApp["composeApp:commonMain"] -->|api| shared
    composeApp -->|api| feature:Auth
    composeApp -->|api| feature:Feed
    composeApp -->|api| feature:Explore
    composeApp -->|api| feature:NewPost
    composeApp -->|api| feature:Likes
    composeApp -->|api| feature:Profile

    composeApp -->|api| core:UI
    composeApp -->|api| core:Navigation
    composeApp -->|api| core:Network
    composeApp -->|api| core:Cache

    feature:Feed -->|api/impl| core:Navigation
    feature:Feed -->|impl| core:UI
    feature:Feed -->|api| core:Network
    feature:Feed -->|api| core:Cache

    server -->|implementation| shared
```

---

# 🏗 Architecture Overview

The architecture follows **Clean Architecture**, **multiplatform modularization**, and **feature isolation**, allowing each layer to scale independently.

## 📱 composeApp/
The cross-platform entry point containing:

- Root `App()` composable  
- Koin bootstrap  
- App theme  
- Navigation host  
- Top & bottom bars  
- Typed route registry  

---

## 🧩 core/
Shared foundation modules:

### `core:UI`
- Theme + color system  
- AppScaffold  
- TopAppBar, BottomNavigation  
- UI config provider  

### `core:Navigation`
- Typed route system using `@Serializable`
- FeatureEntry registration
- Centralized NavHost wiring

### `core:Network`
- Ktor HttpClient wrapper  
- Retry, timeouts, JSON, base-URL config  
- Safe `Result<T>` helpers: `getSafe`, `postSafe`, etc.

### `core:Cache`
- SQLDelight setup  
- Multiplatform drivers (Android/iOS/JS/JVM)  

---

## 🔁 shared/
Pure KMP utilities shared everywhere:

- Expect/actual platform APIs  
- Coroutine dispatchers  
- Logging utils  
- Constants & configuration  

---

## 🧱 feature/
True *vertical slices* containing:

- UI (Compose)  
- State (ViewModel + events + reducers)  
- Domain (models + repositories)  
- Data (remote + cache + mappers)  
- Navigation entry (FeatureEntry)  
- DI module  

Features included: **Auth**, **Feed**, **Explore**, **NewPost**, **Likes**, **Profile**.

Each feature depends on **core**, but never on other features.

---

## 🌐 server/
A lightweight **Ktor** backend:

- Reuses shared data models  
- Exposes REST APIs  
- Demonstrates full multiplatform sharing (e.g., constants, DTOs)

---

# 🧩 DI Bootstrap Example

```kotlin
fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            sharedModule,
            networkModule,
            cacheModule,
            authModule,
            feedModule,
            exploreModule,
            newPostModule,
            likesModule,
            profileModule
        )
    }
}
```

---

# 🚀 Getting Started

### Android
```bash
./gradlew :composeApp:assembleDebug
```

### Desktop (JVM)
```bash
./gradlew :composeApp:run
```

### Web (JS)
```bash
./gradlew :composeApp:jsBrowserDevelopmentRun
```

### Web (Wasm)
```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

### iOS
Open in Xcode or run from IntelliJ.

### Server
```bash
./gradlew :server:run
```

---

# 📄 License

MIT License — see `LICENSE`.

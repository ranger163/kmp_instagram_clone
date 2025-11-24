rootProject.name = "build-logic"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs").apply {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
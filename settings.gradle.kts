pluginManagement.repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

dependencyResolutionManagement.repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
dependencyResolutionManagement.repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

rootProject.name = "Mini Stock App"
include(":app")

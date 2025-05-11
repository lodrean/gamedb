pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") // Add this repository
    }
    plugins {
        // Declare the Compose plugin here
        id("org.jetbrains.kotlin.plugin.compose") version "2.1.20" // Replace YOUR_COMPOSE_PLUGIN_VERSION
        // Other plugins...
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "gamedb"
include(":composeApp")

plugins {
    `kotlin-dsl`
    id("com.github.gmazzo.buildconfig") version "3.1.0" apply false
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
}

dependencies {
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.4")

    // Kotlin Multiplatform
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.9.22")

    // Android
    implementation("com.android.tools.build:gradle:8.2.0")

    // Compose
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.5.12")

    // Room
    implementation("androidx.room:room-gradle-plugin:2.6.1")


    // KSP
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.22-1.0.16")
}

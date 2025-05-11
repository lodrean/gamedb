import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.multiplatform)
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20" // Required for Compose with Kotlin 2.0.0
    alias(libs.plugins.android.application)
    // Add the Compose Desktop plugin here
    alias(libs.plugins.compose) // Assuming you have defined this alias in libs.versions.toml
    alias(libs.plugins.hotReload)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.detekt)
    alias(libs.plugins.buildConfig)
}

kotlin {
    jvmToolchain(11)

    // Add compiler options for expect/actual classes
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    androidTarget {
        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

    jvm()

    wasmJs {
        browser()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinLogging)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.logging)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinInject)
            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
            implementation(libs.multiplatformSettings)
            implementation(libs.kotlinx.datetime)

            // Compose dependencies
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Add androidx.compose.ui dependencies for Preview annotation
            implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
            implementation("androidx.compose.ui:ui-tooling:1.6.0")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(libs.kotlinx.coroutines.test)

            // Add Compose UI testing dependencies
        }

        androidMain.dependencies {
            implementation(libs.androidx.activityCompose)

            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.room.runtime)

            // Add androidx.compose.ui dependencies for Preview annotation
            implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
            implementation("androidx.compose.ui:ui-tooling:1.6.0")
        }

        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.room.runtime)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.room.runtime)
        }

    }
}

android {
    namespace = "lod.gamedb.app"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
        targetSdk = 35

        applicationId = "lod.gamedb.app.androidApp"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

//https://developer.android.com/develop/ui/compose/testing#setup
dependencies {
    androidTestImplementation(libs.androidx.uitest.junit4)
    debugImplementation(libs.androidx.uitest.testManifest)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "gamedb"
            packageVersion = "1.0.0"

            linux {
                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
            }
            windows {
                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
            }
            macOS {
                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
                bundleID = "lod.gamedb.app.desktopApp"
            }
        }
    }
}

//https://github.com/JetBrains/compose-hot-reload
tasks.withType<ComposeHotRun>().configureEach {
    mainClass.set("MainKt")
}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

// Detekt configuration is handled by DetektConventionPlugin

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.serialization)
    with(libs.kotlinInjectKsp) {
        add("kspAndroid", this)
        add("kspJvm", this)
        add("kspWasmJs", this)
        add("kspIosX64", this)
        add("kspIosArm64", this)
        add("kspIosSimulatorArm64", this)
    }
    with(libs.room.compiler) {
        add("kspAndroid", this)
        add("kspJvm", this)
        add("kspIosX64", this)
        add("kspIosArm64", this)
        add("kspIosSimulatorArm64", this)
    }

    // Detekt plugins are configured in DetektConventionPlugin
    // add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-formatting:1.23.4")
    // add("detektPlugins", "com.twitter.compose.rules:detekt:0.0.26")
}

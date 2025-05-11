plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    // Platform targets
    androidTarget()
    jvm()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "core-testing"
            isStatic = true
        }
    }
    
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    
    sourceSets {
        commonMain.dependencies {
            // Core dependencies
            implementation(project(":core:common"))
            
            // Testing dependencies
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            
            // Compose testing
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
    }
}

android {
    namespace = "lod.gamedb.core.testing"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 21
    }
}
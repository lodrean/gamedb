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
            baseName = "core-navigation"
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
            implementation(project(":core:design-system"))
            
            // Compose dependencies
            implementation(compose.runtime)
            implementation(compose.foundation)
            
            // Navigation dependencies
            implementation(libs.androidx.navigation.compose)
            
            // Decompose for navigation
            implementation("com.arkivanov.decompose:decompose:2.2.0")
            implementation("com.arkivanov.decompose:extensions-compose:2.2.0")
        }
        
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "lod.gamedb.core.navigation"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 21
    }
}
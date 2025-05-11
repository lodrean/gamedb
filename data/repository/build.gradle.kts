plugins {
    kotlin("multiplatform")
    id("com.android.library")
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
            baseName = "data-repository"
            isStatic = true
        }
    }
    
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    
    sourceSets {
        commonMain.dependencies {
            // Domain dependencies
            implementation(project(":domain:repository"))
            implementation(project(":domain:model"))
            
            // Data dependencies
            implementation(project(":data:network"))
            implementation(project(":data:local"))
            
            // Core dependencies
            implementation(project(":core:common"))
            
            // Coroutines for async operations
            implementation(libs.kotlinx.coroutines.core)
        }
        
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(project(":core:testing"))
        }
    }
}

android {
    namespace = "lod.gamedb.data.repository"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 21
    }
}
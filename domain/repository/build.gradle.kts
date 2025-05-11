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
            baseName = "domain-repository"
            isStatic = true
        }
    }
    
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    
    sourceSets {
        commonMain.dependencies {
            // Domain model dependency
            implementation(project(":domain:model"))
            
            // Core dependencies
            implementation(project(":core:common"))
            
            // Coroutines for async operations
            implementation(libs.kotlinx.coroutines.core)
        }
        
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

android {
    namespace = "lod.gamedb.domain.repository"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 21
    }
}
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
            baseName = "core-common"
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
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinLogging)
        }
        
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

android {
    namespace = "lod.gamedb.core.common"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 21
    }
}
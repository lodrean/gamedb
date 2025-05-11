plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
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
            baseName = "domain-model"
            isStatic = true
        }
    }
    
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    
    sourceSets {
        commonMain.dependencies {
            // Minimal dependencies for domain models
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
        }
        
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "lod.gamedb.domain.model"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 21
    }
}
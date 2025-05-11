plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight") // Or Room via KSP
    id("com.google.devtools.ksp")
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
            baseName = "data-local"
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
            implementation(project(":domain:model"))
            
            // Core dependencies
            implementation(project(":core:common"))
            
            // Local storage dependencies
            implementation(libs.multiplatformSettings)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.room.runtime)
        }
        
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(project(":core:testing"))
        }
    }
}

android {
    namespace = "lod.gamedb.data.local"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 21
    }
}

// Room configuration
dependencies {
    with(libs.room.compiler) {
        add("kspAndroid", this)
        add("kspJvm", this)
        add("kspIosX64", this)
        add("kspIosArm64", this)
        add("kspIosSimulatorArm64", this)
    }
}

// Room schema location
room {
    schemaDirectory("$projectDir/schemas")
}
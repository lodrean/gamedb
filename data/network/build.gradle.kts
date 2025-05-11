plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("com.github.gmazzo.buildconfig")
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
            baseName = "data-network"
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
            
            // Network dependencies
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
        }
        
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(project(":core:testing"))
        }
        
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        
        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "lod.gamedb.data.network"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 21
    }
}

buildConfig {
    // Read RAWG API key from local.properties
    val localProperties = java.util.Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    val rawgApiKey = if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
        localProperties.getProperty("rawg.api.key", "")
    } else {
        ""
    }

    // Create a BuildConfig field for the API key
    buildConfigField("String", "RAWG_API_KEY", "\"$rawgApiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://api.rawg.io/api\"")
}
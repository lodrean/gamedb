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
            baseName = "feature-details"
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
            implementation(project(":domain:usecase"))
            implementation(project(":domain:model"))
            
            // Core dependencies
            implementation(project(":core:design-system"))
            implementation(project(":core:navigation"))
            implementation(project(":core:common"))
            
            // Compose dependencies
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            
            // ViewModel
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            
            // Image loading
            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
        }
        
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(project(":core:testing"))
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
        
        androidMain.dependencies {
            implementation(compose.uiTooling)
        }
    }
}

android {
    namespace = "lod.gamedb.feature.details"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 21
    }
}
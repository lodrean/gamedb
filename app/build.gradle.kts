plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
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
            baseName = "app"
            isStatic = true
        }
    }
    
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        commonMain.dependencies {
            // Core modules
            implementation(project(":core:common"))
            implementation(project(":core:design-system"))
            implementation(project(":core:navigation"))
            
            // Domain modules
            implementation(project(":domain:model"))
            implementation(project(":domain:repository"))
            implementation(project(":domain:usecase"))
            
            // Data modules
            implementation(project(":data:network"))
            implementation(project(":data:local"))
            implementation(project(":data:repository"))
            
            // Feature modules
            implementation(project(":feature:search"))
            implementation(project(":feature:details"))
            implementation(project(":feature:favorites"))
            
            // Compose dependencies
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            
            // Dependency Injection
            implementation(libs.kotlinInject)
            
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
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
            implementation(libs.androidx.activityCompose)
            implementation(libs.kotlinx.coroutines.android)
        }
        
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "lod.gamedb.app"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 21
        targetSdk = 35
        
        applicationId = "lod.gamedb.app"
        versionCode = 1
        versionName = "1.0.0"
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

compose.desktop {
    application {
        mainClass = "lod.gamedb.app.MainKt"
        
        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg, 
                          org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi, 
                          org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb)
            packageName = "gamedb"
            packageVersion = "1.0.0"
        }
    }
}

// KSP configuration for Kotlin Inject
dependencies {
    with(libs.kotlinInjectKsp) {
        add("kspAndroid", this)
        add("kspJvm", this)
        add("kspWasmJs", this)
        add("kspIosX64", this)
        add("kspIosArm64", this)
        add("kspIosSimulatorArm64", this)
    }
}
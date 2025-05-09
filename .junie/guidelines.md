# GameDB Development Guidelines

This document provides essential information for developers working on the GameDB project, a Kotlin Multiplatform application with Compose UI targeting Android, iOS, Desktop, and Web platforms.

## Build/Configuration Instructions

### Prerequisites
- JDK 17 or higher
- Android SDK (path should be set in `local.properties`)
- [KDoctor](https://github.com/Kotlin/kdoctor) for system compatibility check

### Platform-Specific Build Instructions

#### Android
- **Run on device/emulator**: Open project in Android Studio and run the imported android run configuration
- **Build APK**: `./gradlew :composeApp:assembleDebug`
- **APK location**: `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

#### Desktop
- **Run application**: `./gradlew :composeApp:run`
- **Run with hot reload**: `./gradlew :composeApp:jvmRunHot`
- **Build distribution**: Configure in `compose.desktop.application` block in `composeApp/build.gradle.kts`

#### iOS
- **Run on device/simulator**: Open `iosApp/iosApp.xcproject` in Xcode or use Kotlin Multiplatform Mobile plugin for Android Studio
- **Note**: iOS targets require macOS for building

#### Web (WASM)
- **Run browser application**: `./gradlew :composeApp:wasmJsBrowserDevelopmentRun --continue`

## Testing Information

### Test Configuration

The project uses Kotlin Test framework with additional libraries:
- Compose UI Test for UI component testing
- Kotlinx Coroutines Test for testing coroutine-based code

### Running Tests

#### Platform-Specific Test Commands
- **Android UI tests**: `./gradlew :composeApp:connectedDebugAndroidTest`
- **Desktop tests**: `./gradlew :composeApp:jvmTest`
- **iOS simulator tests**: `./gradlew :composeApp:iosSimulatorArm64Test`
- **Web tests**: `./gradlew :composeApp:wasmJsBrowserTest`

### Adding New Tests

#### Unit Tests
1. Create a new test class in the appropriate source set:
   - Common tests: `composeApp/src/commonTest/kotlin/`
   - Platform-specific tests: `composeApp/src/<platform>Test/kotlin/`

2. Example of a simple unit test:
```kotlin
class SimpleTest {
    @Test
    fun testBasicFunctionality() {
        val result = 2 + 2
        assertEquals(4, result, "Basic arithmetic should work correctly")
    }
}
```

#### UI Tests
1. Create a new test class that uses the Compose UI testing framework:

```kotlin
@OptIn(ExperimentalTestApi::class)
class MyComposeTest {
    @Test
    fun testUIComponent() = runComposeUiTest {
        // Set up your UI component
        setContent {
            // Your Composable here
        }

        // Interact with and verify UI elements
        onNodeWithTag("my_button").performClick()
        onNodeWithTag("my_text").assertTextEquals("Expected Text")
    }
}
```

## Additional Development Information

### Project Structure
- **composeApp**: Main module containing shared and platform-specific code
- **iosApp**: Xcode project for iOS
- **gradle/libs.versions.toml**: Central dependency management

### Key Dependencies
- **Compose Multiplatform**: UI framework
- **Ktor**: Networking
- **Room**: Database (with multiplatform support)
- **Kotlinx Serialization**: JSON parsing
- **Kotlin Inject**: Dependency injection
- **Multiplatform Settings**: Preferences storage

### Code Style Guidelines
- Follow Kotlin coding conventions
- Use composable functions for UI components
- Implement platform-specific code in respective source sets
- Use the repository pattern for data access

### Debugging Tips
- For UI issues, use Compose Preview when possible
- Add logging with `println("[DEBUG_LOG] message")` in tests
- Use platform-specific debugging tools when needed:
  - Android: Android Studio profiler
  - iOS: Xcode debugging tools
  - Desktop: JVM debugging in IntelliJ IDEA

### Hot Reload
The project supports hot reload for faster development:
- Desktop: `./gradlew :composeApp:jvmRunHot`
- Configuration in `composeCompiler` block in build.gradle.kts

### Known Issues
- iOS targets cannot be built on non-macOS systems
- Some experimental APIs are used which may change in future Kotlin versions

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

### Dependency Management Guidelines
- Prefer libraries officially supported by Google and JetBrains
- Minimize the use of third-party libraries to reduce maintenance burden
- When adding a new dependency, evaluate if it's actively maintained and compatible with Kotlin Multiplatform
- Document any third-party library usage with justification in code comments

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

### Junie Documentation System
The project uses Junie for documentation and guidelines management:

#### What is Junie?
Junie is a documentation and guidelines system integrated with the IDE that helps maintain consistent development practices across the team. It provides:
- Centralized storage for project guidelines
- Easy access to documentation from within the IDE
- Support for Markdown formatting for rich documentation

#### Using Junie
- Access guidelines by clicking on the Junie icon in the IDE toolbar
- Guidelines are stored in the `.junie` directory at the project root
- Edit guidelines by modifying the Markdown files in the `.junie` directory
- Changes to guidelines are tracked in version control

#### Adding New Guidelines
1. Create a new Markdown file in the `.junie` directory
2. Follow the existing formatting conventions
3. Link to the new guidelines from the main guidelines.md file if appropriate
4. Commit the changes to version control

### Latest Trends in Compose Multiplatform Architecture (2025)

#### Current Trends and Best Practices
Based on the latest developments with Compose Multiplatform 1.8.0, several architectural patterns have emerged as best practices:

1. **Modular Architecture** - Breaking down applications into separate Gradle modules by feature and layer
   - Improves build times by enabling parallel compilation
   - Enforces separation of concerns
   - Facilitates team collaboration by reducing merge conflicts
   - Enables better reusability of components

2. **Clean Architecture** - Implementing domain-driven design with clear separation of concerns
   - Domain layer contains business logic and is platform-independent
   - Data layer handles data operations and implements repository interfaces
   - Presentation layer (UI) consumes use cases from the domain layer
   - Dependencies point inward (UI → Domain ← Data)

3. **Stable iOS Support** - Now that Compose Multiplatform for iOS is stable with version 1.8.0, shared UI code across platforms is production-ready
   - Consistent UI across platforms with minimal platform-specific code
   - Improved performance and stability on iOS
   - Better integration with SwiftUI when needed

4. **Decompose for Navigation** - Using Decompose library for multi-platform navigation
   - Type-safe navigation with shared navigation logic
   - Support for deep linking
   - State preservation during configuration changes
   - Integration with platform-specific navigation systems

5. **KMP Wizard Templates** - New project templates that support modular architectures out of the box
   - Pre-configured module structure
   - Integration with popular libraries
   - Best practices for dependency management

#### Recommended Project Structure
```
root/
├── buildSrc/                    # Shared build logic
├── gradle/                      # Gradle configuration
├── app/                         # Application module (thin layer)
├── core/
│   ├── common/                  # Common utilities and extensions
│   ├── design-system/           # UI components, themes, and resources
│   ├── navigation/              # Navigation components and routes
│   └── testing/                 # Test utilities and mocks
├── data/
│   ├── network/                 # Remote API implementation
│   ├── local/                   # Local database implementation
│   └── repository/              # Repository implementations
├── domain/
│   ├── model/                   # Domain models
│   ├── repository/              # Repository interfaces
│   └── usecase/                 # Use cases/interactors
└── feature/
    ├── search/                  # Game search feature
    ├── details/                 # Game details feature
    └── favorites/               # Favorites feature
```

#### Implementation Guidelines
1. **Module Dependencies**
   - Feature modules depend on domain and core modules
   - Data modules depend on domain modules
   - Domain modules have no dependencies on other modules
   - Core modules have minimal dependencies

2. **Dependency Injection**
   - Use Koin or Kotlin Inject for dependency injection
   - Configure DI in the app module
   - Provide dependencies at the appropriate level

3. **API Key Handling**
   - Store API keys in local.properties (not in version control)
   - Access keys via BuildConfig
   - Provide fallback mechanisms for missing keys

4. **Testing Strategy**
   - Unit test domain logic extensively
   - Use fake repositories for testing use cases
   - Test UI components with Compose UI Test
   - Implement integration tests for critical flows

### Known Issues
- iOS targets cannot be built on non-macOS systems
- Some experimental APIs are used which may change in future Kotlin versions

# GameDB Multiplatform App

A Kotlin Multiplatform application for searching and browsing games across Android, iOS, Desktop, and Web platforms.

## Screenshots

[Add screenshots here]

## Technologies

- Kotlin Multiplatform
- Compose Multiplatform
- Room Database
- Ktor Client
- Kotlinx Serialization
- Kotlin Coroutines & Flow
- Kotlin Inject for Dependency Injection
- Multiplatform Settings for Preferences

## Setup

### Prerequisites
- JDK 17 or higher
- Android Studio Arctic Fox or higher
- Xcode 13+ (for iOS)
- Check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
- Add `local.properties` file to the project root and set a path to Android SDK there

### Running the Project

#### Android
To run the application on android device/emulator:  
- Open project in Android Studio and run imported android run configuration

To build the application bundle:
- Run `./gradlew :composeApp:assembleDebug`
- Find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

Run android UI tests on the connected device: 
```
./gradlew :composeApp:connectedDebugAndroidTest
```

#### Desktop
Run the desktop application: 
```
./gradlew :composeApp:run
```

Run the desktop with hot reload: 
```
./gradlew :composeApp:jvmRunHot
```

Run desktop UI tests: 
```
./gradlew :composeApp:jvmTest
```

#### iOS
To run the application on iPhone device/simulator:
- Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
- Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio

Run iOS simulator UI tests: 
```
./gradlew :composeApp:iosSimulatorArm64Test
```

#### Web (Wasm)
Run the browser application: 
```
./gradlew :composeApp:wasmJsBrowserDevelopmentRun --continue
```

Run browser UI tests: 
```
./gradlew :composeApp:wasmJsBrowserTest
```

## Features

- Search for games using the IGDB API
- View detailed information about games
- Save favorite games for offline access
- Cross-platform UI with Compose Multiplatform
- Shared business logic across all platforms

## Live Demo

Check out the [live demo](https://YOUR-USERNAME.github.io/gamedb/) of the web application deployed on GitHub Pages.

## Architecture

The project follows a clean architecture approach with the following layers:
- UI (Compose Multiplatform)
- ViewModel (Platform-independent)
- Domain (Business logic)
- Data (Repositories, API clients, local storage)

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)

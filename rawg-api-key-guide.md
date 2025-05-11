# Guide: How to Obtain and Use a RAWG API Key in GameDB

This guide will walk you through the process of obtaining an API key from the RAWG Video Games Database and securely adding it to the GameDB application.

## What is RAWG?

RAWG is one of the largest video game databases with over 500,000 games for 50 platforms. The GameDB application uses RAWG's API to search for games and display their details.

## Why You Need an API Key

The application is currently showing a 401 authentication error because it's using a placeholder API key. To fix this, you need to obtain your own API key from RAWG.

## Step 1: Create a RAWG Account

1. Go to [RAWG.io](https://rawg.io/)
2. Click on the "Sign Up" button in the top right corner
3. You can sign up using your email or through social media accounts (Google, Discord, etc.)
4. Complete the registration process

## Step 2: Get Your API Key

1. After signing in, go to the [RAWG API Developer Portal](https://rawg.io/apidocs)
2. Click on the "Get API Key" button
3. Fill out the required information:
   - Name of your application (e.g., "GameDB")
   - Select a platform (e.g., "Personal Project")
   - Provide a brief description of how you'll use the API
4. Agree to the terms of service
5. Click "Generate" to create your API key
6. Copy your API key - you'll need it for the next steps

## Step 3: Add Your API Key to the Application

### Option 1: Using local.properties (Recommended for Android Development)

For Android development, you can add your API key to the `local.properties` file:

1. Open the `local.properties` file in the root of the project
2. Add the following line:
   ```properties
   rawg.api.key=your-actual-api-key-from-rawg
   ```
3. The Android app will automatically use this API key when built

### Option 2: Modifying ApiKeyUtil (Not Recommended for Production)

You can modify the fallback placeholder in the `ApiKeyUtil` implementation for your platform:

1. Open the ApiKeyUtil file for your platform:
   - Android: `composeApp/src/androidMain/kotlin/lod/gamedb/app/util/ApiKeyUtil.kt`
   - Desktop: `composeApp/src/jvmMain/kotlin/lod/gamedb/app/util/ApiKeyUtil.kt`
   - iOS: `composeApp/src/iosMain/kotlin/lod/gamedb/app/util/ApiKeyUtil.kt`
   - Web: `composeApp/src/wasmJsMain/kotlin/lod/gamedb/app/util/ApiKeyUtil.kt`

2. Replace the placeholder in the fallback return statement:
   ```kotlin
   // Change this line:
   return "YOUR_ACTUAL_RAWG_API_KEY_HERE"

   // To:
   return "your-actual-api-key-from-rawg"
   ```

### Option 3: Using Environment Variables and Secure Storage (Recommended)

For better security, you can use environment variables and platform-specific secure storage methods:

#### Desktop (JVM) Version

The app now uses `ApiKeyUtil` to get the API key from multiple sources:

1. Set the environment variable before running the app:
   ```
   # Windows (Command Prompt)
   set RAWG_API_KEY=your-actual-api-key-from-rawg

   # Windows (PowerShell)
   $env:RAWG_API_KEY="your-actual-api-key-from-rawg"

   # macOS/Linux
   export RAWG_API_KEY=your-actual-api-key-from-rawg
   ```

2. Alternatively, you can add this to your IDE's run configuration:
   - In IntelliJ IDEA, edit the run configuration
   - Go to the "Environment variables" field
   - Add `RAWG_API_KEY=your-actual-api-key-from-rawg`

3. You can also save the API key to a properties file:
   ```kotlin
   // Save the API key
   ApiKeyUtil.saveRawgApiKey("your-actual-api-key-from-rawg")
   ```

   This will create a file at `~/.gamedb/api.properties` with your API key.

#### Android Version

The app now uses `ApiKeyUtil` to get the API key from multiple sources:

1. From `local.properties` (recommended for development):
   - Add `rawg.api.key=your-actual-api-key-from-rawg` to your `local.properties` file
   - This is automatically included in the app's BuildConfig during compilation

2. From environment variables:
   ```
   # Windows (Command Prompt)
   set RAWG_API_KEY=your-actual-api-key-from-rawg

   # Windows (PowerShell)
   $env:RAWG_API_KEY="your-actual-api-key-from-rawg"

   # macOS/Linux
   export RAWG_API_KEY=your-actual-api-key-from-rawg
   ```

3. From a properties file in the app's private storage:
   ```kotlin
   // Save the API key
   ApiKeyUtil.saveRawgApiKey(context, "your-actual-api-key-from-rawg")
   ```

   This will create a file in the app's private storage with your API key.

#### iOS Version

The app now uses `ApiKeyUtil` to get the API key from multiple sources:

1. From environment variables (recommended for development):
   - Open the Xcode project in `iosApp/iosApp.xcodeproj`
   - Go to the target's "Edit Scheme" menu
   - Select "Run" from the left sidebar
   - Go to the "Arguments" tab
   - Under "Environment Variables", add a variable named "RAWG_API_KEY" with your API key as the value

2. From Info.plist (recommended for production):
   - Open `iosApp/iosApp/Info.plist`
   - Add a new entry with key `RawgApiKey` and your API key as the value

The app will check these sources in order and use the first valid API key it finds.

#### Web Version

The app now uses `ApiKeyUtil` to get the API key from URL parameters:

1. Add the API key as a URL parameter when accessing the app:
   ```
   https://your-app-url.com/?apiKey=your-actual-api-key-from-rawg
   ```

The app will extract the API key from the URL parameter. If no API key is found in the URL, it will fall back to a placeholder.

2. For production, you should implement a more secure method, such as:
   - Storing the API key on your server and making API requests through a proxy
   - Using a backend service to handle API requests
   - Implementing a token-based authentication system

## Step 4: Verify the Connection

After adding your API key, run the application and try searching for a game. If everything is set up correctly:

1. The search should work without any authentication errors
2. Game results should appear in the search list
3. You should be able to view game details

## API Usage Limits

The free tier of the RAWG API has the following limits:
- 20,000 requests per month
- No commercial use

If you need more requests or want to use the API commercially, check RAWG's pricing plans on their [API page](https://rawg.io/apidocs).

## Security Considerations

- Never commit your API key to version control
- Use environment variables or secure storage for production applications
- Consider implementing API key rotation for enhanced security
- The RAWG API key is for client identification only and doesn't provide access to sensitive data

## Troubleshooting

If you're still experiencing issues after adding your API key:

1. Verify that your API key is correct
2. Check your internet connection
3. Look for any error messages in the application logs
4. Ensure you're not exceeding the API rate limits
5. Try restarting the application

For additional help, refer to the [RAWG API documentation](https://api.rawg.io/docs/).

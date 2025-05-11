package lod.gamedb.app.util

import android.content.Context
import gamedb.composeApp.BuildConfig
import java.io.File
import java.util.Properties

/**
 * Utility class for handling API keys in the Android app.
 */
object ApiKeyUtil {
    /**
     * Get the RAWG API key from various sources.
     *
     * This method tries to get the API key from:
     * 1. Environment variable (for CI/CD environments)
     * 2. A properties file in the app's files directory
     * 3. BuildConfig.RAWG_API_KEY (from local.properties)
     * 4. Falls back to a placeholder if none are available
     *
     * @param context The Android context
     * @return The API key or a placeholder if not found
     */
    fun getRawgApiKey(context: Context): String {
        // Try to get from environment variable first (useful for CI/CD)
        System.getenv("RAWG_API_KEY")?.let { return it }

        // Try to read from a properties file in the app's files directory
        try {
            val propertiesFile = File(context.filesDir, "api.properties")
            if (propertiesFile.exists()) {
                val properties = Properties()
                properties.load(propertiesFile.inputStream())
                properties.getProperty("rawg.api.key")?.let { return it }
            }
        } catch (e: Exception) {
            // Log error but continue with fallback
            println("Error reading API key from properties file: ${e.message}")
        }

        // Try to get from BuildConfig (from local.properties)
        if (BuildConfig.RAWG_API_KEY.isNotEmpty() && BuildConfig.RAWG_API_KEY != "\"\"") {
            return BuildConfig.RAWG_API_KEY
        }

        // Fallback to placeholder with informative message
        return "your-actual-api-key-from-rawg"
    }

    /**
     * Save the RAWG API key to a properties file.
     *
     * @param context The Android context
     * @param apiKey The API key to save
     * @return True if the key was saved successfully, false otherwise
     */
    fun saveRawgApiKey(context: Context, apiKey: String): Boolean {
        return try {
            val propertiesFile = File(context.filesDir, "api.properties")
            val properties = Properties()

            // Load existing properties if file exists
            if (propertiesFile.exists()) {
                properties.load(propertiesFile.inputStream())
            }

            // Set the API key
            properties.setProperty("rawg.api.key", apiKey)

            // Save the properties
            properties.store(propertiesFile.outputStream(), "RAWG API Key")
            true
        } catch (e: Exception) {
            // Log error
            println("Error saving API key to properties file: ${e.message}")
            false
        }
    }
}

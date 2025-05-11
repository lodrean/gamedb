package lod.gamedb.app.util

import java.io.File
import java.util.Properties

/**
 * Utility object for handling API keys in the JVM app.
 */
object ApiKeyUtil {
    /**
     * Get the RAWG API key from various sources.
     *
     * This method tries to get the API key from:
     * 1. Environment variable (for development and CI/CD environments)
     * 2. A properties file in the user's home directory
     * 3. Falls back to a placeholder if neither is available
     *
     * @return The API key or a placeholder if not found
     */
    fun getRawgApiKey(): String {
        // Try to get from environment variable first (useful for development and CI/CD)
        System.getenv("RAWG_API_KEY")?.let { return it }

        // Try to read from a properties file in the user's home directory
        try {
            val userHome = System.getProperty("user.home")
            val propertiesFile = File(userHome, ".gamedb/api.properties")
            if (propertiesFile.exists()) {
                val properties = Properties()
                properties.load(propertiesFile.inputStream())
                properties.getProperty("rawg.api.key")?.let { return it }
            }
        } catch (e: Exception) {
            // Log error but continue with fallback
            println("Error reading API key from properties file: ${e.message}")
        }

        // Fallback to placeholder with informative message
        return "YOUR_ACTUAL_RAWG_API_KEY_HERE"
    }

    /**
     * Save the RAWG API key to a properties file.
     *
     * @param apiKey The API key to save
     * @return True if the key was saved successfully, false otherwise
     */
    fun saveRawgApiKey(apiKey: String): Boolean {
        return try {
            val userHome = System.getProperty("user.home")
            val gamedbDir = File(userHome, ".gamedb")
            if (!gamedbDir.exists()) {
                gamedbDir.mkdirs()
            }

            val propertiesFile = File(gamedbDir, "api.properties")
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

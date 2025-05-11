package lod.gamedb.app.util

import kotlinx.browser.window

/**
 * Utility object for handling API keys in the Web app.
 */
object ApiKeyUtil {
    /**
     * Get the RAWG API key from URL parameters.
     *
     * This method tries to get the API key from:
     * 1. URL query parameter (e.g., ?apiKey=your-key)
     * 2. Falls back to a placeholder if not available
     *
     * @return The API key or a placeholder if not found
     */
    fun getRawgApiKey(): String {
        // Try to get from URL query parameter
        try {
            val urlParams = window.location.search
            if (urlParams.isNotEmpty()) {
                // Remove the leading '?' and split by '&'
                val params = urlParams.substring(1).split("&")

                // Find the apiKey parameter
                for (param in params) {
                    val keyValue = param.split("=")
                    if (keyValue.size == 2 && keyValue[0] == "apiKey") {
                        return keyValue[1]
                    }
                }
            }
        } catch (e: Exception) {
            // Ignore errors and use fallback
        }

        // Fallback to placeholder with informative message
        return "YOUR_ACTUAL_RAWG_API_KEY_HERE"
    }
}

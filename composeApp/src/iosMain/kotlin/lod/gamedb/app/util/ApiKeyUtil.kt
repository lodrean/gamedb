package lod.gamedb.app.util

import platform.Foundation.NSBundle
import platform.Foundation.NSProcessInfo

/**
 * Utility object for handling API keys in the iOS app.
 */
object ApiKeyUtil {
    /**
     * Get the RAWG API key from various sources.
     *
     * This method tries to get the API key from:
     * 1. Environment variable (for development and CI/CD environments)
     * 2. Info.plist (for production)
     * 3. Falls back to a placeholder if neither is available
     *
     * @return The API key or a placeholder if not found
     */
    fun getRawgApiKey(): String {
        // Try to get from environment variable first (useful for development and CI/CD)
        NSProcessInfo.processInfo.environment["RAWG_API_KEY"]?.let { return it as String }

        // Try to get from Info.plist (for production)
        NSBundle.mainBundle.infoDictionary?.get("RawgApiKey")?.let { return it as String }

        // Fallback to placeholder with informative message
        return "YOUR_ACTUAL_RAWG_API_KEY_HERE"
    }
}

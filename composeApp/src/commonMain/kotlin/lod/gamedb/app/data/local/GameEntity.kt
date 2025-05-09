package lod.gamedb.app.data.local

import kotlinx.datetime.Clock

/**
 * Entity class for storing game data in the Room database.
 * This class represents the structure of the game data as stored in the local database.
 * 
 * Note: Room annotations are applied in platform-specific code.
 * This class is designed to be used with Room on Android, JVM, and iOS platforms.
 */
data class GameEntity(
    val id: Long,

    val name: String,

    val description: String?,

    val backgroundImage: String?,

    val released: String?,

    val rating: Double?,

    val metacritic: Int?,

    /**
     * The search query that was used to find this game.
     * Used for caching purposes.
     */
    val searchQuery: String,

    /**
     * The page number where this game was found.
     * Used for pagination and caching purposes.
     */
    val pageNumber: Int,

    /**
     * Timestamp when this entity was created/updated.
     * Used for cache invalidation.
     */
    val timestamp: Long = Clock.System.now().toEpochMilliseconds()
)

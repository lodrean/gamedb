package lod.gamedb.app.domain.repository

import kotlinx.coroutines.flow.Flow
import lod.gamedb.app.domain.model.Game

/**
 * Repository interface for games.
 * This interface defines the operations that can be performed on the game repository.
 */
interface GameRepository {
    /**
     * Search for games by query.
     *
     * @param query The search query.
     * @param page The page number (1-based).
     * @param forceRefresh Whether to force a refresh from the remote data source.
     * @return A Flow emitting lists of Game objects containing the search results.
     */
    suspend fun searchGames(
        query: String,
        page: Int = 1,
        forceRefresh: Boolean = false
    ): Flow<List<Game>>

    /**
     * Get details for a specific game by ID.
     *
     * @param id The game ID.
     * @param forceRefresh Whether to force a refresh from the remote data source.
     * @return A Flow emitting a Game object containing the game details.
     */
    suspend fun getGameDetails(
        id: Long,
        forceRefresh: Boolean = false
    ): Flow<Game>

    /**
     * Clear the cache for a specific search query.
     *
     * @param query The search query.
     */
    suspend fun clearCache(query: String)

    /**
     * Clear all caches older than the specified duration in milliseconds.
     *
     * @param maxAgeMs The maximum age of the cache in milliseconds.
     */
    suspend fun clearOldCaches(maxAgeMs: Long)
}
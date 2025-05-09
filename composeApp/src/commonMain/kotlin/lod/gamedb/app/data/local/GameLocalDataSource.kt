package lod.gamedb.app.data.local

/**
 * Interface for the local data source for games.
 * This interface defines the operations that can be performed on the local database.
 * Each platform will provide its own implementation of this interface.
 */
interface GameLocalDataSource {
    /**
     * Get all games for a specific search query and page number.
     *
     * @param query The search query.
     * @param page The page number.
     * @return A list of games matching the query and page.
     */
    suspend fun getGamesByQueryAndPage(query: String, page: Int): List<GameEntity>

    /**
     * Get all games for a specific search query across all pages.
     *
     * @param query The search query.
     * @return A list of games matching the query.
     */
    suspend fun getGamesByQuery(query: String): List<GameEntity>

    /**
     * Insert games into the database.
     * If a game with the same ID already exists, it will be replaced.
     *
     * @param games The games to insert.
     */
    suspend fun insertGames(games: List<GameEntity>)

    /**
     * Delete all games older than the specified timestamp.
     *
     * @param timestamp The timestamp to compare against.
     * @return The number of games deleted.
     */
    suspend fun deleteOldGames(timestamp: Long): Int

    /**
     * Delete all games for a specific search query.
     *
     * @param query The search query.
     * @return The number of games deleted.
     */
    suspend fun deleteGamesByQuery(query: String): Int
}
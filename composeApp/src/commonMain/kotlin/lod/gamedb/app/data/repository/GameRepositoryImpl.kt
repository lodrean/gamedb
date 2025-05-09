package lod.gamedb.app.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import lod.gamedb.app.data.local.GameLocalDataSource
import lod.gamedb.app.data.mapper.toDomainModel
import lod.gamedb.app.data.mapper.toGameDomainModel
import lod.gamedb.app.data.mapper.toEntity
import lod.gamedb.app.data.remote.GameRemoteDataSource
import lod.gamedb.app.domain.model.Game
import lod.gamedb.app.domain.repository.GameRepository
import kotlinx.datetime.Clock

/**
 * Implementation of the GameRepository interface.
 * This class is responsible for coordinating data operations between the local and remote data sources.
 */
class GameRepositoryImpl(
    private val remoteDataSource: GameRemoteDataSource,
    private val localDataSource: GameLocalDataSource
) : GameRepository {
    /**
     * Search for games by query.
     *
     * @param query The search query.
     * @param page The page number (1-based).
     * @param forceRefresh Whether to force a refresh from the remote data source.
     * @return A Flow emitting lists of Game objects containing the search results.
     */
    override suspend fun searchGames(
        query: String,
        page: Int,
        forceRefresh: Boolean
    ): Flow<List<Game>> = flow {
        // First, try to get data from local cache if not forcing refresh
        if (!forceRefresh) {
            val localGames = localDataSource.getGamesByQueryAndPage(query, page)
            if (localGames.isNotEmpty()) {
                emit(localGames.toGameDomainModel())
            }
        }

        // Then, fetch from network and update cache
        try {
            val remoteGames = remoteDataSource.searchGames(query, page)
            val domainGames = remoteGames.toDomainModel()

            // Update cache
            localDataSource.insertGames(domainGames.toEntity(query, page))

            // Emit the fresh data
            emit(domainGames)
        } catch (e: Exception) {
            // If we already emitted cached data, don't throw the exception
            val localGames = localDataSource.getGamesByQueryAndPage(query, page)
            if (localGames.isEmpty()) {
                throw e
            }
        }
    }

    /**
     * Get details for a specific game by ID.
     *
     * @param id The game ID.
     * @param forceRefresh Whether to force a refresh from the remote data source.
     * @return A Flow emitting a Game object containing the game details.
     */
    override suspend fun getGameDetails(
        id: Long,
        forceRefresh: Boolean
    ): Flow<Game> = flow {
        // For game details, we don't have a specific cache strategy yet
        // In a real app, we might want to cache game details separately
        try {
            val gameDetails = remoteDataSource.getGameDetails(id)
            emit(gameDetails.toDomainModel())
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Clear the cache for a specific search query.
     *
     * @param query The search query.
     */
    override suspend fun clearCache(query: String) {
        localDataSource.deleteGamesByQuery(query)
    }

    /**
     * Clear all caches older than the specified duration in milliseconds.
     *
     * @param maxAgeMs The maximum age of the cache in milliseconds.
     */
    override suspend fun clearOldCaches(maxAgeMs: Long) {
        val cutoffTime = Clock.System.now().toEpochMilliseconds() - maxAgeMs
        localDataSource.deleteOldGames(cutoffTime)
    }
}

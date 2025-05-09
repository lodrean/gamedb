package lod.gamedb.app.data.local

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * In-memory implementation of the GameLocalDataSource interface.
 * This implementation stores games in memory and is suitable for platforms
 * where Room is not available (JVM, iOS, WASM).
 */
class InMemoryGameLocalDataSource : GameLocalDataSource {
    private val mutex = Mutex()
    private val games = mutableListOf<GameEntity>()

    override suspend fun getGamesByQueryAndPage(query: String, page: Int): List<GameEntity> {
        return mutex.withLock {
            games.filter { it.searchQuery == query && it.pageNumber == page }
                .sortedByDescending { it.timestamp }
        }
    }

    override suspend fun getGamesByQuery(query: String): List<GameEntity> {
        return mutex.withLock {
            games.filter { it.searchQuery == query }
                .sortedWith(compareBy<GameEntity> { it.pageNumber }.thenByDescending { it.timestamp })
        }
    }

    override suspend fun insertGames(games: List<GameEntity>) {
        mutex.withLock {
            // Remove existing games with the same IDs
            val gameIds = games.map { it.id }.toSet()
            this.games.removeAll { it.id in gameIds }
            
            // Add the new games
            this.games.addAll(games)
        }
    }

    override suspend fun deleteOldGames(timestamp: Long): Int {
        return mutex.withLock {
            val initialSize = games.size
            games.removeAll { it.timestamp < timestamp }
            initialSize - games.size
        }
    }

    override suspend fun deleteGamesByQuery(query: String): Int {
        return mutex.withLock {
            val initialSize = games.size
            games.removeAll { it.searchQuery == query }
            initialSize - games.size
        }
    }
}
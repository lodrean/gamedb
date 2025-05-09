package lod.gamedb.app.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase

/**
 * Room DAO for the games table.
 */
@Dao
interface GameDao {
    /**
     * Get all games for a specific search query and page number.
     *
     * @param query The search query.
     * @param page The page number.
     * @return A list of games matching the query and page.
     */
    @Query("SELECT * FROM games WHERE searchQuery = :query AND pageNumber = :page ORDER BY timestamp DESC")
    suspend fun getGamesByQueryAndPage(query: String, page: Int): List<AndroidGameEntity>

    /**
     * Get all games for a specific search query across all pages.
     *
     * @param query The search query.
     * @return A list of games matching the query.
     */
    @Query("SELECT * FROM games WHERE searchQuery = :query ORDER BY pageNumber ASC, timestamp DESC")
    suspend fun getGamesByQuery(query: String): List<AndroidGameEntity>

    /**
     * Insert games into the database.
     * If a game with the same ID already exists, it will be replaced.
     *
     * @param games The games to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<AndroidGameEntity>)

    /**
     * Delete all games older than the specified timestamp.
     *
     * @param timestamp The timestamp to compare against.
     * @return The number of games deleted.
     */
    @Query("DELETE FROM games WHERE timestamp < :timestamp")
    suspend fun deleteOldGames(timestamp: Long): Int

    /**
     * Delete all games for a specific search query.
     *
     * @param query The search query.
     * @return The number of games deleted.
     */
    @Query("DELETE FROM games WHERE searchQuery = :query")
    suspend fun deleteGamesByQuery(query: String): Int
}

/**
 * Room database for the app.
 */
@Database(entities = [AndroidGameEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}

/**
 * Android implementation of the GameLocalDataSource interface using Room.
 */
class AndroidGameLocalDataSource(private val gameDao: GameDao) : GameLocalDataSource {
    override suspend fun getGamesByQueryAndPage(query: String, page: Int): List<GameEntity> {
        return gameDao.getGamesByQueryAndPage(query, page).map { it.toCommonEntity() }
    }

    override suspend fun getGamesByQuery(query: String): List<GameEntity> {
        return gameDao.getGamesByQuery(query).map { it.toCommonEntity() }
    }

    override suspend fun insertGames(games: List<GameEntity>) {
        gameDao.insertGames(games.map { it.toAndroidEntity() })
    }

    override suspend fun deleteOldGames(timestamp: Long): Int {
        return gameDao.deleteOldGames(timestamp)
    }

    override suspend fun deleteGamesByQuery(query: String): Int {
        return gameDao.deleteGamesByQuery(query)
    }
}

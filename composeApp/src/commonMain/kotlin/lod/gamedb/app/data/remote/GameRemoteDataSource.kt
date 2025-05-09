package lod.gamedb.app.data.remote

import lod.gamedb.app.data.model.GameDto

/**
 * Remote data source for games.
 * This class is responsible for fetching game data from the RAWG API.
 */
class GameRemoteDataSource(private val gameApi: GameApi) {
    /**
     * Search for games by query.
     *
     * @param query The search query.
     * @param page The page number (1-based).
     * @param pageSize The number of results per page.
     * @return A list of GameDto objects containing the search results.
     */
    suspend fun searchGames(
        query: String,
        page: Int = 1,
        pageSize: Int = 20
    ): List<GameDto> {
        return gameApi.searchGames(query, page, pageSize).results
    }

    /**
     * Get details for a specific game by ID.
     *
     * @param id The game ID.
     * @return A GameDto containing the game details.
     */
    suspend fun getGameDetails(id: Long): GameDto {
        return gameApi.getGameDetails(id)
    }
}
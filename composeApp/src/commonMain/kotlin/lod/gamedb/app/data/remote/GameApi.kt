package lod.gamedb.app.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import lod.gamedb.app.data.model.GameSearchResponseDto

/**
 * API client for the RAWG Video Games Database API.
 * This class is responsible for making HTTP requests to the RAWG API.
 */
class GameApi(
    private val httpClient: HttpClient,
    private val apiKey: String,
    private val baseUrl: String = "https://api.rawg.io/api"
) {
    /**
     * Search for games by query.
     *
     * @param query The search query.
     * @param page The page number (1-based).
     * @param pageSize The number of results per page.
     * @return A GameSearchResponseDto containing the search results.
     */
    suspend fun searchGames(
        query: String,
        page: Int = 1,
        pageSize: Int = 20
    ): GameSearchResponseDto {
        return httpClient.get("$baseUrl/games") {
            parameter("key", apiKey)
            parameter("search", query)
            parameter("page", page)
            parameter("page_size", pageSize)
        }.body()
    }

    /**
     * Get details for a specific game by ID.
     *
     * @param id The game ID.
     * @return A GameDto containing the game details.
     */
    suspend fun getGameDetails(id: Long): lod.gamedb.app.data.model.GameDto {
        return httpClient.get("$baseUrl/games/$id") {
            parameter("key", apiKey)
        }.body()
    }
}
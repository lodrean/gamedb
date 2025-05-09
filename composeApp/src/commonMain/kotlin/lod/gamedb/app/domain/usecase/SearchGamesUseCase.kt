package lod.gamedb.app.domain.usecase

import kotlinx.coroutines.flow.Flow
import lod.gamedb.app.domain.model.Game
import lod.gamedb.app.domain.repository.GameRepository

/**
 * Use case for searching games.
 * This class encapsulates the business logic for searching games.
 */
class SearchGamesUseCase(private val gameRepository: GameRepository) {
    /**
     * Search for games by query.
     *
     * @param query The search query.
     * @param page The page number (1-based).
     * @param forceRefresh Whether to force a refresh from the remote data source.
     * @return A Flow emitting lists of Game objects containing the search results.
     */
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        forceRefresh: Boolean = false
    ): Flow<List<Game>> {
        return gameRepository.searchGames(query, page, forceRefresh)
    }
}
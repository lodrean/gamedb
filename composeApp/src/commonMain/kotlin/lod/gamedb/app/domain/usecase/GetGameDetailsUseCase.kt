package lod.gamedb.app.domain.usecase

import kotlinx.coroutines.flow.Flow
import lod.gamedb.app.domain.model.Game
import lod.gamedb.app.domain.repository.GameRepository

/**
 * Use case for getting game details.
 * This class encapsulates the business logic for retrieving details of a specific game.
 */
class GetGameDetailsUseCase(private val gameRepository: GameRepository) {
    /**
     * Get details for a specific game by ID.
     *
     * @param id The game ID.
     * @param forceRefresh Whether to force a refresh from the remote data source.
     * @return A Flow emitting a Game object containing the game details.
     */
    suspend operator fun invoke(
        id: Long,
        forceRefresh: Boolean = false
    ): Flow<Game> {
        return gameRepository.getGameDetails(id, forceRefresh)
    }
}
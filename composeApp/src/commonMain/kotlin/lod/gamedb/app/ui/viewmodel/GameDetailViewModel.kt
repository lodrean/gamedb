package lod.gamedb.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lod.gamedb.app.domain.model.Game
import lod.gamedb.app.domain.usecase.GetGameDetailsUseCase
import lod.gamedb.app.ui.model.GameUiModel

/**
 * ViewModel for the game detail screen.
 * This class is responsible for fetching and managing the game details data.
 */
class GameDetailViewModel(
    private val getGameDetailsUseCase: GetGameDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameDetailUiState())
    val uiState: StateFlow<GameDetailUiState> = _uiState.asStateFlow()

    /**
     * Load the game details.
     *
     * @param gameId The ID of the game to load.
     * @param forceRefresh Whether to force a refresh from the remote data source.
     */
    fun loadGameDetails(gameId: Long, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                getGameDetailsUseCase(gameId, forceRefresh)
                    .catch { e ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = e.message ?: "Unknown error"
                        )
                    }
                    .collectLatest { game ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            game = game.toUiModel()
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    /**
     * Retry loading the game details.
     */
    fun retry() {
        val gameId = _uiState.value.game?.id ?: return
        loadGameDetails(gameId, true)
    }

    /**
     * Convert a domain model Game to a UI model GameUiModel.
     */
    private fun Game.toUiModel(): GameUiModel {
        return GameUiModel(
            id = id,
            title = name,
            description = description ?: "",
            imageUrl = imageUrl,
            releaseDate = releaseDate,
            rating = rating?.toString(),
            metacritic = metacritic?.toString()
        )
    }
}

/**
 * UI state for the game detail screen.
 */
data class GameDetailUiState(
    val isLoading: Boolean = false,
    val game: GameUiModel? = null,
    val error: String? = null
)

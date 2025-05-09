package lod.gamedb.app.ui.model

/**
 * UI model for game data.
 * This class represents the structure of the game data as used by the UI layer.
 */
data class GameUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val releaseDate: String?,
    val rating: String?,
    val metacritic: String?
)

/**
 * UI state for the game search screen.
 * This class represents the state of the UI for the game search screen.
 */
data class GameSearchUiState(
    val isLoading: Boolean = false,
    val games: List<GameUiModel> = emptyList(),
    val error: String? = null,
    val searchQuery: String = "",
    val isLastPage: Boolean = false
)
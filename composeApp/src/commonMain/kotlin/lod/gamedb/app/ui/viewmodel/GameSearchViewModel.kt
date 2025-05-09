package lod.gamedb.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lod.gamedb.app.data.mapper.toUiModel
import lod.gamedb.app.domain.usecase.SearchGamesUseCase
import lod.gamedb.app.ui.model.GameSearchUiState

/**
 * ViewModel for the game search screen.
 * This class is responsible for managing the UI state and handling user interactions.
 */
class GameSearchViewModel(
    private val searchGamesUseCase: SearchGamesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(GameSearchUiState())
    val uiState: StateFlow<GameSearchUiState> = _uiState.asStateFlow()

    private var currentQuery = ""
    private var currentPage = 1
    private var isLastPage = false

    /**
     * Search for games by query.
     * This will reset the current page to 1 and clear the existing games list.
     *
     * @param query The search query.
     */
    fun searchGames(query: String) {
        if (query.isBlank()) return

        currentQuery = query
        currentPage = 1
        isLastPage = false

        _uiState.update { 
            it.copy(
                isLoading = true, 
                games = emptyList(),
                error = null,
                searchQuery = query
            ) 
        }

        viewModelScope.launch {
            try {
                searchGamesUseCase(query, currentPage).collect { games ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            games = games.toUiModel(),
                            isLastPage = games.isEmpty()
                        ) 
                    }
                    isLastPage = games.isEmpty()
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        error = e.message ?: "Unknown error occurred"
                    ) 
                }
            }
        }
    }

    /**
     * Load more games for the current query.
     * This will increment the current page and append the new games to the existing list.
     */
    fun loadMoreGames() {
        if (_uiState.value.isLoading || currentQuery.isBlank() || isLastPage) return

        currentPage++

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                searchGamesUseCase(currentQuery, currentPage).collect { newGames ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            games = it.games + newGames.toUiModel(),
                            isLastPage = newGames.isEmpty()
                        ) 
                    }
                    isLastPage = newGames.isEmpty()
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        error = e.message ?: "Unknown error occurred"
                    ) 
                }
            }
        }
    }

    /**
     * Retry the last search operation.
     * This is useful when an error occurs and the user wants to try again.
     */
    fun retry() {
        if (currentQuery.isBlank()) return
        
        if (currentPage == 1) {
            searchGames(currentQuery)
        } else {
            loadMoreGames()
        }
    }
}
package lod.gamedb.app.ui.preview

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import lod.gamedb.app.domain.model.Game
import lod.gamedb.app.domain.repository.GameRepository
import lod.gamedb.app.domain.usecase.SearchGamesUseCase
import lod.gamedb.app.theme.AppTheme
import lod.gamedb.app.ui.screen.GameSearchScreen
import lod.gamedb.app.ui.viewmodel.GameSearchViewModel

/**
 * A mock implementation of GameRepository for previews.
 */
class MockGameRepository : GameRepository {
    // Sample game data
    private val sampleGames = listOf(
        Game(
            id = 1,
            name = "The Witcher 3: Wild Hunt",
            description = "The Witcher 3: Wild Hunt is a 2015 action role-playing game developed and published by Polish developer CD Projekt Red.",
            imageUrl = null,
            releaseDate = "May 19, 2015",
            rating = 4.8,
            metacritic = 93
        ),
        Game(
            id = 2,
            name = "Red Dead Redemption 2",
            description = "Red Dead Redemption 2 is a 2018 action-adventure game developed and published by Rockstar Games.",
            imageUrl = null,
            releaseDate = "October 26, 2018",
            rating = 4.9,
            metacritic = 97
        ),
        Game(
            id = 3,
            name = "The Legend of Zelda: Breath of the Wild",
            description = "The Legend of Zelda: Breath of the Wild is a 2017 action-adventure game developed and published by Nintendo.",
            imageUrl = null,
            releaseDate = "March 3, 2017",
            rating = 4.9,
            metacritic = 97
        )
    )

    // Empty list for loading state preview
    private val emptyGames = emptyList<Game>()

    override suspend fun searchGames(query: String, page: Int, forceRefresh: Boolean): Flow<List<Game>> {
        // Return sample games for normal preview, empty list for loading preview
        return flowOf(if (query == "loading") emptyGames else sampleGames)
    }

    override suspend fun getGameDetails(id: Long, forceRefresh: Boolean): Flow<Game> {
        // Return the first sample game for simplicity
        return flowOf(sampleGames.first())
    }

    override suspend fun clearCache(query: String) {
        // No-op for preview
    }

    override suspend fun clearOldCaches(maxAgeMs: Long) {
        // No-op for preview
    }
}

/**
 * Preview for the GameSearchScreen.
 * This preview shows how the main search screen of the app looks with sample data.
 */
@Preview
@Composable
fun GameSearchScreenPreview() {
    AppTheme {
        // Create a mock repository and use case
        val repository = remember { MockGameRepository() }
        val useCase = remember { SearchGamesUseCase(repository) }

        // Create a view model with the mock use case
        val viewModel = remember { GameSearchViewModel(useCase) }

        // Trigger a search to populate the UI
        viewModel.searchGames("zelda")

        // Use the GameSearchScreen composable with our view model
        GameSearchScreen(
            viewModel = viewModel,
            onGameClick = { /* No-op in preview */ }
        )
    }
}

/**
 * Preview for the GameSearchScreen in loading state.
 */
@Preview
@Composable
fun GameSearchScreenLoadingPreview() {
    AppTheme {
        // Create a mock repository that returns empty results to simulate loading
        val repository = remember { MockGameRepository() }
        val useCase = remember { SearchGamesUseCase(repository) }

        // Create a view model with the mock use case
        val viewModel = remember { GameSearchViewModel(useCase) }

        // Trigger a search with "loading" to get empty results and show loading state
        viewModel.searchGames("loading")

        // Use the GameSearchScreen composable with our view model
        GameSearchScreen(
            viewModel = viewModel,
            onGameClick = { /* No-op in preview */ }
        )
    }
}

/**
 * A mock implementation of GameRepository that returns empty results for previewing error states.
 */
class EmptyResultsGameRepository : GameRepository {
    override suspend fun searchGames(query: String, page: Int, forceRefresh: Boolean): Flow<List<Game>> {
        // Always return empty results
        return flowOf(emptyList())
    }

    override suspend fun getGameDetails(id: Long, forceRefresh: Boolean): Flow<Game> {
        // Return a placeholder game
        return flowOf(Game(
            id = 0,
            name = "Not Found",
            description = "Game not found",
            imageUrl = null,
            releaseDate = null,
            rating = null,
            metacritic = null
        ))
    }

    override suspend fun clearCache(query: String) {
        // No-op for preview
    }

    override suspend fun clearOldCaches(maxAgeMs: Long) {
        // No-op for preview
    }
}

/**
 * Preview for the GameSearchScreen with an error.
 * Note: This is a simplified preview that shows the "No games found" state.
 */
@Preview
@Composable
fun GameSearchScreenErrorPreview() {
    AppTheme {
        // Create a repository that returns empty results and a use case
        val repository = remember { EmptyResultsGameRepository() }
        val useCase = remember { SearchGamesUseCase(repository) }

        // Create a view model with the mock use case
        val viewModel = remember { GameSearchViewModel(useCase) }

        // Trigger a search to show empty results
        viewModel.searchGames("nonexistent")

        // Use the GameSearchScreen composable with our view model
        GameSearchScreen(
            viewModel = viewModel,
            onGameClick = { /* No-op in preview */ }
        )
    }
}

/**
 * Desktop-specific preview for the GameSearchScreen.
 * This preview shows how the app would look in a desktop window.
 */
@Preview
@Composable
fun GameSearchScreenDesktopPreview() {
    // Create a mock repository and use case
    val repository = remember { MockGameRepository() }
    val useCase = remember { SearchGamesUseCase(repository) }

    // Create a view model with the mock use case
    val viewModel = remember { GameSearchViewModel(useCase) }

    // Trigger a search to populate the UI
    viewModel.searchGames("zelda")

    // Simulate a desktop window
    Window(
        title = "GameDB Desktop Preview",
        state = WindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = { /* No-op in preview */ }
    ) {
        AppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                GameSearchScreen(
                    viewModel = viewModel,
                    onGameClick = { /* No-op in preview */ }
                )
            }
        }
    }
}

/**
 * Desktop-specific preview for the GameSearchScreen in loading state.
 * This preview shows how the loading state would look in a desktop window.
 */
@Preview
@Composable
fun GameSearchScreenDesktopLoadingPreview() {
    // Create a mock repository and use case
    val repository = remember { MockGameRepository() }
    val useCase = remember { SearchGamesUseCase(repository) }

    // Create a view model with the mock use case
    val viewModel = remember { GameSearchViewModel(useCase) }

    // Trigger a search with "loading" to get empty results and show loading state
    viewModel.searchGames("loading")

    // Simulate a desktop window
    Window(
        title = "GameDB Desktop Loading Preview",
        state = WindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = { /* No-op in preview */ }
    ) {
        AppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                GameSearchScreen(
                    viewModel = viewModel,
                    onGameClick = { /* No-op in preview */ }
                )
            }
        }
    }
}

/**
 * Desktop-specific preview for the GameSearchScreen with an error.
 * This preview shows how the error state would look in a desktop window.
 */
@Preview
@Composable
fun GameSearchScreenDesktopErrorPreview() {
    // Create a repository that returns empty results and a use case
    val repository = remember { EmptyResultsGameRepository() }
    val useCase = remember { SearchGamesUseCase(repository) }

    // Create a view model with the mock use case
    val viewModel = remember { GameSearchViewModel(useCase) }

    // Trigger a search to show empty results
    viewModel.searchGames("nonexistent")

    // Simulate a desktop window
    Window(
        title = "GameDB Desktop Error Preview",
        state = WindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = { /* No-op in preview */ }
    ) {
        AppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                GameSearchScreen(
                    viewModel = viewModel,
                    onGameClick = { /* No-op in preview */ }
                )
            }
        }
    }
}

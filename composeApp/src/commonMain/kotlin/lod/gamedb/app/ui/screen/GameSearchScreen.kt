package lod.gamedb.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import lod.gamedb.app.ui.components.GameItem
import lod.gamedb.app.ui.components.SearchBar
import lod.gamedb.app.ui.model.GameSearchUiState
import lod.gamedb.app.ui.model.GameUiModel
import lod.gamedb.app.ui.viewmodel.GameSearchViewModel

/**
 * Composable for the game search screen.
 *
 * @param viewModel The view model for the game search screen.
 * @param onGameClick Callback for when a game item is clicked.
 */
@Composable
fun GameSearchScreen(
    viewModel: GameSearchViewModel,
    onGameClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    GameSearchContent(
        uiState = uiState,
        onSearch = { query -> viewModel.searchGames(query) },
        onLoadMore = { viewModel.loadMoreGames() },
        onRetry = { viewModel.retry() },
        onGameClick = onGameClick
    )
}

/**
 * Content of the game search screen.
 */
@Composable
private fun GameSearchContent(
    uiState: GameSearchUiState,
    onSearch: (String) -> Unit,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    onGameClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Search Bar
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = { /* No-op, handled in onSearch */ },
            onSearch = onSearch,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Error Message
        if (uiState.error != null) {
            ErrorMessage(
                message = uiState.error,
                onRetry = onRetry,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Results
        GamesList(
            games = uiState.games,
            isLoading = uiState.isLoading,
            isLastPage = uiState.isLastPage,
            onLoadMore = onLoadMore,
            onGameClick = onGameClick,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
    }
}

/**
 * Error message with retry button.
 */
@Composable
private fun ErrorMessage(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

/**
 * List of games with load more functionality.
 */
@Composable
private fun GamesList(
    games: List<lod.gamedb.app.ui.model.GameUiModel>,
    isLoading: Boolean,
    isLastPage: Boolean,
    onLoadMore: () -> Unit,
    onGameClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // Load more when reaching the end
    LaunchedEffect(games.size, isLoading) {
        if (!isLoading && !isLastPage && games.isNotEmpty()) {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItemsCount = games.size

            if (lastVisibleItemIndex >= totalItemsCount - 5) {
                onLoadMore()
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(games) { game ->
            GameItem(
                game = game,
                onClick = { onGameClick(game.id) }
            )
        }

        if (isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        if (isLastPage && games.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No more games to load",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    if (games.isEmpty() && !isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No games found. Try a different search term.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

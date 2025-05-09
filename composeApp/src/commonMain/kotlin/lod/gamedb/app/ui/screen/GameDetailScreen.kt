package lod.gamedb.app.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import lod.gamedb.app.ui.model.GameUiModel
import lod.gamedb.app.ui.viewmodel.GameDetailViewModel

/**
 * Composable for the game detail screen.
 *
 * @param gameId The ID of the game to display.
 * @param viewModel The view model for the game detail screen.
 * @param onBackClick Callback for when the back button is clicked.
 */
@Composable
fun GameDetailScreen(
    gameId: Long,
    viewModel: GameDetailViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load game details when the screen is first displayed
    LaunchedEffect(gameId) {
        viewModel.loadGameDetails(gameId)
    }

    GameDetailContent(
        uiState = uiState,
        onRetry = { viewModel.retry() },
        onBackClick = onBackClick
    )
}

/**
 * Content of the game detail screen.
 */
@Composable
private fun GameDetailContent(
    uiState: lod.gamedb.app.ui.viewmodel.GameDetailUiState,
    onRetry: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back button
        Button(
            onClick = onBackClick,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Back")
        }

        // Loading indicator
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Error message
        if (uiState.error != null) {
            ErrorMessage(
                message = uiState.error,
                onRetry = onRetry,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Game details
        uiState.game?.let { game ->
            GameDetails(
                game = game,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            )
        }
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
 * Game details content.
 */
@Composable
private fun GameDetails(
    game: GameUiModel,
    modifier: Modifier = Modifier
) {
    var expandedDescription by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        // Game image
        // Placeholder for image (in a real app, we would use AsyncImage from Coil)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = game.title.take(1).uppercase(),
                style = MaterialTheme.typography.headlineLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Game title
        Text(
            text = game.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Game metadata
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Release date
            if (game.releaseDate != null) {
                Text(
                    text = "Released: ${game.releaseDate}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Rating
            if (game.rating != null) {
                Text(
                    text = "Rating: ${game.rating}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Metacritic
            if (game.metacritic != null) {
                Text(
                    text = "Metacritic: ${game.metacritic}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Expandable description
        Text(
            text = game.description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = if (expandedDescription) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clickable { expandedDescription = !expandedDescription }
        )

        // "Show more" button
        if (game.description.length > 100) {
            Button(
                onClick = { expandedDescription = !expandedDescription },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(if (expandedDescription) "Show less" else "Show more")
            }
        }
    }
}

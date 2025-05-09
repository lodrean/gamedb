package lod.gamedb.app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lod.gamedb.app.domain.repository.GameRepository
import lod.gamedb.app.domain.usecase.GetGameDetailsUseCase
import lod.gamedb.app.domain.usecase.SearchGamesUseCase
import lod.gamedb.app.navigation.NavHost
import lod.gamedb.app.navigation.rememberNavController
import lod.gamedb.app.theme.AppTheme
import lod.gamedb.app.ui.screen.GameDetailScreen
import lod.gamedb.app.ui.screen.GameSearchScreen
import lod.gamedb.app.ui.viewmodel.GameDetailViewModel
import lod.gamedb.app.ui.viewmodel.GameSearchViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun App(
    gameRepository: GameRepository? = null
) = AppTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (gameRepository != null) {
            // Real implementation with repository
            val navController = rememberNavController()

            // Create use cases
            val searchGamesUseCase = SearchGamesUseCase(gameRepository)
            val getGameDetailsUseCase = GetGameDetailsUseCase(gameRepository)

            // Create view models
            val searchViewModel = remember { GameSearchViewModel(searchGamesUseCase) }
            val detailViewModel = remember { GameDetailViewModel(getGameDetailsUseCase) }

            // Set up navigation
            NavHost(
                navController = navController,
                homeContent = {
                    GameSearchScreen(
                        viewModel = searchViewModel,
                        onGameClick = { gameId ->
                            navController.navigateToGameDetail(gameId)
                        }
                    )
                },
                gameDetailContent = { gameId ->
                    GameDetailScreen(
                        gameId = gameId,
                        viewModel = detailViewModel,
                        onBackClick = {
                            navController.navigateToHome()
                        }
                    )
                }
            )
        } else {
            // Preview/placeholder implementation
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Game Database",
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "A multiplatform game database app",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(32.dp))

                CircularProgressIndicator()

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

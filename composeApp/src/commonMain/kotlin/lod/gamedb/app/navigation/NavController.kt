package lod.gamedb.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

/**
 * A simple navigation controller for the app.
 * This class manages the current destination and provides methods to navigate between destinations.
 */
class NavController {
    /**
     * The current destination.
     */
    var currentDestination by mutableStateOf<NavDestination>(NavDestination.Home)
        private set

    /**
     * Navigate to the specified destination.
     *
     * @param destination The destination to navigate to.
     */
    fun navigate(destination: NavDestination) {
        currentDestination = destination
    }

    /**
     * Navigate to the game detail screen.
     *
     * @param gameId The ID of the game to display.
     */
    fun navigateToGameDetail(gameId: Long) {
        navigate(NavDestination.GameDetail(gameId))
    }

    /**
     * Navigate back to the home screen.
     */
    fun navigateToHome() {
        navigate(NavDestination.Home)
    }
}

/**
 * Remember a NavController instance.
 * This function uses rememberSaveable with a custom Saver to preserve the navigation state across configuration changes.
 *
 * @return A remembered NavController instance.
 */
@Composable
fun rememberNavController(): NavController {
    return rememberSaveable(
        saver = androidx.compose.runtime.saveable.Saver(
            save = { navController ->
                when (val destination = navController.currentDestination) {
                    is NavDestination.Home -> "home"
                    is NavDestination.GameDetail -> "game_detail:${destination.gameId}"
                }
            },
            restore = { value ->
                val navController = NavController()
                when {
                    value == "home" -> navController.navigate(NavDestination.Home)
                    value.startsWith("game_detail:") -> {
                        val gameId = value.removePrefix("game_detail:").toLongOrNull() ?: 0L
                        navController.navigate(NavDestination.GameDetail(gameId))
                    }
                }
                navController
            }
        )
    ) {
        NavController()
    }
}

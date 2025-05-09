package lod.gamedb.app.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable

/**
 * A composable that displays the appropriate screen based on the current destination.
 *
 * @param navController The navigation controller.
 * @param homeContent The content to display for the home destination.
 * @param gameDetailContent The content to display for the game detail destination.
 */
@Composable
fun NavHost(
    navController: NavController,
    homeContent: @Composable () -> Unit,
    gameDetailContent: @Composable (gameId: Long) -> Unit
) {
    // Use AnimatedContent to animate transitions between destinations
    AnimatedContent(
        targetState = navController.currentDestination,
        transitionSpec = {
            // Define the enter and exit animations
            val enterTransition = fadeIn(animationSpec = tween(300))
            val exitTransition = fadeOut(animationSpec = tween(300))
            
            // Combine the enter and exit animations
            enterTransition.togetherWith(exitTransition)
        }
    ) { destination ->
        // Display the appropriate content based on the destination
        when (destination) {
            is NavDestination.Home -> homeContent()
            is NavDestination.GameDetail -> gameDetailContent(destination.gameId)
        }
    }
}
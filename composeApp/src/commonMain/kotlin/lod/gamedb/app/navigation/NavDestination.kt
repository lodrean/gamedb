package lod.gamedb.app.navigation

/**
 * Type-safe navigation destinations for the app.
 * This sealed class defines all the possible navigation destinations in the app.
 */
sealed class NavDestination {
    /**
     * Home screen destination.
     * This is the main screen of the app, showing the game search functionality.
     */
    object Home : NavDestination()

    /**
     * Game detail screen destination.
     * This screen shows detailed information about a specific game.
     *
     * @param gameId The ID of the game to display.
     */
    data class GameDetail(val gameId: Long) : NavDestination()
}

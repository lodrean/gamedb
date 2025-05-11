import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension
import lod.gamedb.app.App
import lod.gamedb.app.di.AppFactory
import lod.gamedb.app.util.ApiKeyUtil

fun main() = application {
    // Initialize the repository with an API key from ApiKeyUtil
    // This is more secure than hardcoding the API key
    val apiKey = ApiKeyUtil.getRawgApiKey() // Get API key from environment or properties file
    val gameRepository = AppFactory.createGameRepository(apiKey)

    Window(
        title = "gamedb",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        // Pass the repository to the App
        App(gameRepository)
    }
}

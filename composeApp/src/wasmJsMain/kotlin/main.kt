import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import lod.gamedb.app.App
import lod.gamedb.app.di.AppFactory
import lod.gamedb.app.util.ApiKeyUtil
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // Initialize the repository with an API key from URL parameters
    // This is more secure than hardcoding the API key
    val apiKey = ApiKeyUtil.getRawgApiKey() // Get API key from URL parameter
    val gameRepository = AppFactory.createGameRepository(apiKey)

    val body = document.body ?: return
    ComposeViewport(body) {
        // Pass the repository to the App
        App(gameRepository)
    }
}

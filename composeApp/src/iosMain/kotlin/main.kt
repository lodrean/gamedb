import androidx.compose.ui.window.ComposeUIViewController
import lod.gamedb.app.App
import lod.gamedb.app.di.AppFactory
import lod.gamedb.app.util.ApiKeyUtil
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    // Initialize the repository with an API key from ApiKeyUtil
    // This is more secure than hardcoding the API key
    val apiKey = ApiKeyUtil.getRawgApiKey() // Get API key from environment or Info.plist
    val gameRepository = AppFactory.createGameRepository(apiKey)

    return ComposeUIViewController { 
        // Pass the repository to the App
        App(gameRepository) 
    }
}

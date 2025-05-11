package lod.gamedb.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import lod.gamedb.app.di.AppFactory
import lod.gamedb.app.util.ApiKeyUtil

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the repository with an API key from ApiKeyUtil
        // This is more secure than hardcoding the API key
        val apiKey = ApiKeyUtil.getRawgApiKey(this) // Get API key from properties file or environment
        val gameRepository = AppFactory.createGameRepository(apiKey)

        setContent { 
            // Pass the repository to the App
            App(gameRepository) 
        }
    }
}

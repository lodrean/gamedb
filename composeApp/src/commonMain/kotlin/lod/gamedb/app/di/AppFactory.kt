package lod.gamedb.app.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

import lod.gamedb.app.data.local.GameLocalDataSource
import lod.gamedb.app.data.local.InMemoryGameLocalDataSource
import lod.gamedb.app.data.remote.GameApi
import lod.gamedb.app.data.remote.GameRemoteDataSource
import lod.gamedb.app.data.repository.GameRepositoryImpl
import lod.gamedb.app.domain.repository.GameRepository

/**
 * Factory for creating the app's dependencies.
 * This class is responsible for creating and providing instances of the app's dependencies.
 */
object AppFactory {
    /**
     * Create a HttpClient with the necessary plugins.
     */
    fun createHttpClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json { // 'json' is now resolved
                    prettyPrint = true // Optional: for easier debugging
                    ignoreUnknownKeys = true // Optional: ignore properties in JSON that are not in your data class
                })
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : io.ktor.client.plugins.logging.Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
            }
        }
    }

    /**
     * Create a GameApi instance.
     *
     * @param httpClient The HttpClient to use.
     * @param apiKey The RAWG API key.
     * @return A GameApi instance.
     */
    fun createGameApi(httpClient: HttpClient, apiKey: String): GameApi {
        return GameApi(httpClient, apiKey)
    }

    /**
     * Create a GameRemoteDataSource instance.
     *
     * @param gameApi The GameApi to use.
     * @return A GameRemoteDataSource instance.
     */
    fun createGameRemoteDataSource(gameApi: GameApi): GameRemoteDataSource {
        return GameRemoteDataSource(gameApi)
    }

    /**
     * Create a GameLocalDataSource instance.
     * This method returns an in-memory implementation by default.
     * Platform-specific implementations can override this method.
     *
     * @return A GameLocalDataSource instance.
     */
    fun createGameLocalDataSource(): GameLocalDataSource {
        return InMemoryGameLocalDataSource()
    }

    /**
     * Create a GameRepository instance.
     *
     * @param remoteDataSource The GameRemoteDataSource to use.
     * @param localDataSource The GameLocalDataSource to use.
     * @return A GameRepository instance.
     */
    fun createGameRepository(
        remoteDataSource: GameRemoteDataSource,
        localDataSource: GameLocalDataSource
    ): GameRepository {
        return GameRepositoryImpl(remoteDataSource, localDataSource)
    }

    /**
     * Create a GameRepository instance with default dependencies.
     *
     * @param apiKey The RAWG API key.
     * @return A GameRepository instance.
     */
    fun createGameRepository(apiKey: String): GameRepository {
        val httpClient = createHttpClient()
        val gameApi = createGameApi(httpClient, apiKey)
        val remoteDataSource = createGameRemoteDataSource(gameApi)
        val localDataSource = createGameLocalDataSource()
        return createGameRepository(remoteDataSource, localDataSource)
    }
}

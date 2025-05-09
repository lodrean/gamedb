package lod.gamedb.app.domain.model

/**
 * Domain model for game data.
 * This class represents the structure of the game data as used by the business logic.
 */
data class Game(
    val id: Long,
    val name: String,
    val description: String?,
    val imageUrl: String?,
    val releaseDate: String?,
    val rating: Double?,
    val metacritic: Int?
)
package lod.gamedb.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for game data from the RAWG API.
 * This class represents the structure of the game data as received from the API.
 */
@Serializable
data class GameDto(
    @SerialName("id")
    val id: Long,
    
    @SerialName("name")
    val name: String,
    
    @SerialName("description")
    val description: String? = null,
    
    @SerialName("background_image")
    val backgroundImage: String? = null,
    
    @SerialName("released")
    val released: String? = null,
    
    @SerialName("rating")
    val rating: Double? = null,
    
    @SerialName("metacritic")
    val metacritic: Int? = null
)

/**
 * Data Transfer Object for the game search response from the RAWG API.
 * This class represents the structure of the search response as received from the API.
 */
@Serializable
data class GameSearchResponseDto(
    @SerialName("count")
    val count: Int,
    
    @SerialName("next")
    val next: String? = null,
    
    @SerialName("previous")
    val previous: String? = null,
    
    @SerialName("results")
    val results: List<GameDto>
)
package lod.gamedb.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * iOS-specific implementation of GameEntity with Room annotations.
 * This class is used with Room database on iOS.
 */
@Entity(tableName = "games")
data class IosGameEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String?,
    val backgroundImage: String?,
    val released: String?,
    val rating: Double?,
    val metacritic: Int?,
    val searchQuery: String,
    val pageNumber: Int,
    val timestamp: Long
)

/**
 * Extension function to convert a GameEntity to an IosGameEntity.
 */
fun GameEntity.toIosEntity(): IosGameEntity {
    return IosGameEntity(
        id = id,
        name = name,
        description = description,
        backgroundImage = backgroundImage,
        released = released,
        rating = rating,
        metacritic = metacritic,
        searchQuery = searchQuery,
        pageNumber = pageNumber,
        timestamp = timestamp
    )
}

/**
 * Extension function to convert an IosGameEntity to a GameEntity.
 */
fun IosGameEntity.toCommonEntity(): GameEntity {
    return GameEntity(
        id = id,
        name = name,
        description = description,
        backgroundImage = backgroundImage,
        released = released,
        rating = rating,
        metacritic = metacritic,
        searchQuery = searchQuery,
        pageNumber = pageNumber,
        timestamp = timestamp
    )
}
package lod.gamedb.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Android-specific implementation of GameEntity with Room annotations.
 * This class is used with Room database on Android.
 */
@Entity(tableName = "games")
data class AndroidGameEntity(
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
 * Extension function to convert a GameEntity to an AndroidGameEntity.
 */
fun GameEntity.toAndroidEntity(): AndroidGameEntity {
    return AndroidGameEntity(
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
 * Extension function to convert an AndroidGameEntity to a GameEntity.
 */
fun AndroidGameEntity.toCommonEntity(): GameEntity {
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

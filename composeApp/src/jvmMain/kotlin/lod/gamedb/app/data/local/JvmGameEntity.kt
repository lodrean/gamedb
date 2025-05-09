package lod.gamedb.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * JVM-specific implementation of GameEntity with Room annotations.
 * This class is used with Room database on JVM.
 */
@Entity(tableName = "games")
data class JvmGameEntity(
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
 * Extension function to convert a GameEntity to a JvmGameEntity.
 */
fun GameEntity.toJvmEntity(): JvmGameEntity {
    return JvmGameEntity(
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
 * Extension function to convert a JvmGameEntity to a GameEntity.
 */
fun JvmGameEntity.toCommonEntity(): GameEntity {
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

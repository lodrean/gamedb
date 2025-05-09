package lod.gamedb.app.data.local

/**
 * Wasm-specific implementation of GameEntity.
 * This is a dummy implementation since Room is not supported on Wasm.
 * It's provided for API compatibility only.
 */
data class WasmGameEntity(
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
 * Extension function to convert a GameEntity to a WasmGameEntity.
 */
fun GameEntity.toWasmEntity(): WasmGameEntity {
    return WasmGameEntity(
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
 * Extension function to convert a WasmGameEntity to a GameEntity.
 */
fun WasmGameEntity.toCommonEntity(): GameEntity {
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
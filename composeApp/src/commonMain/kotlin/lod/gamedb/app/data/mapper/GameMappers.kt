package lod.gamedb.app.data.mapper

import lod.gamedb.app.data.local.GameEntity
import lod.gamedb.app.data.model.GameDto
import lod.gamedb.app.domain.model.Game
import lod.gamedb.app.ui.model.GameUiModel

/**
 * Extension function to convert a GameDto to a Game domain model.
 */
fun GameDto.toDomainModel(): Game {
    return Game(
        id = id,
        name = name,
        description = description,
        imageUrl = backgroundImage,
        releaseDate = released,
        rating = rating,
        metacritic = metacritic
    )
}

/**
 * Extension function to convert a list of GameDto to a list of Game domain models.
 */
fun List<GameDto>.toDomainModel(): List<Game> {
    return map { it.toDomainModel() }
}

/**
 * Extension function to convert a GameEntity to a Game domain model.
 */
fun GameEntity.toDomainModel(): Game {
    return Game(
        id = id,
        name = name,
        description = description,
        imageUrl = backgroundImage,
        releaseDate = released,
        rating = rating,
        metacritic = metacritic
    )
}

/**
 * Extension function to convert a list of GameEntity to a list of Game domain models.
 */
fun List<GameEntity>.toGameDomainModel(): List<Game> {
    return map { it.toDomainModel() }
}

/**
 * Extension function to convert a Game domain model to a GameEntity.
 */
fun Game.toEntity(searchQuery: String, pageNumber: Int): GameEntity {
    return GameEntity(
        id = id,
        name = name,
        description = description,
        backgroundImage = imageUrl,
        released = releaseDate,
        rating = rating,
        metacritic = metacritic,
        searchQuery = searchQuery,
        pageNumber = pageNumber
    )
}

/**
 * Extension function to convert a list of Game domain models to a list of GameEntity.
 */
fun List<Game>.toEntity(searchQuery: String, pageNumber: Int): List<GameEntity> {
    return map { it.toEntity(searchQuery, pageNumber) }
}

/**
 * Extension function to convert a Game domain model to a GameUiModel.
 */
fun Game.toUiModel(): GameUiModel {
    return GameUiModel(
        id = id,
        title = name,
        description = description ?: "",
        imageUrl = imageUrl,
        releaseDate = releaseDate,
        rating = rating?.toString(),
        metacritic = metacritic?.toString()
    )
}

/**
 * Extension function to convert a list of Game domain models to a list of GameUiModel.
 */
fun List<Game>.toUiModel(): List<GameUiModel> {
    return map { it.toUiModel() }
}

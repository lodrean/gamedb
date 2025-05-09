package lod.gamedb.app.data.local

import androidx.room.Entity as RoomEntity
import androidx.room.PrimaryKey as RoomPrimaryKey

/**
 * Android implementation of Room Entity annotation.
 * This delegates to the actual Room Entity annotation.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
actual annotation class Entity(
    actual val tableName: String = ""
)

/**
 * Android implementation of Room PrimaryKey annotation.
 * This delegates to the actual Room PrimaryKey annotation.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.BINARY)
actual annotation class PrimaryKey(
    actual val autoGenerate: Boolean = false
)

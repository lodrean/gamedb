package lod.gamedb.app.data.local

/**
 * Multiplatform annotation for Room Entity.
 * This is an expect class that will be implemented differently on each platform.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
expect annotation class Entity(val tableName: String = "")

/**
 * Multiplatform annotation for Room PrimaryKey.
 * This is an expect class that will be implemented differently on each platform.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.BINARY)
expect annotation class PrimaryKey(val autoGenerate: Boolean = false)
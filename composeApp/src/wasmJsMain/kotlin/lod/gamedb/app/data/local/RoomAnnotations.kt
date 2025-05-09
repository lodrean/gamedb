package lod.gamedb.app.data.local

/**
 * WasmJs implementation of Room Entity annotation.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
actual annotation class Entity(actual val tableName: String = "")

/**
 * WasmJs implementation of Room PrimaryKey annotation.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.BINARY)
actual annotation class PrimaryKey(actual val autoGenerate: Boolean = false)
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Convention plugin for Room database configuration.
 * This plugin applies the Room plugin.
 */
class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply required plugins
            pluginManager.apply("androidx.room")
        }
    }
}

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Main convention plugin for GameDB application.
 * This plugin applies all the necessary plugins for the GameDB application.
 */
class GameDbConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply all required plugins
            pluginManager.apply("KotlinMultiplatformConventionPlugin")
            pluginManager.apply("AndroidApplicationConventionPlugin")
            pluginManager.apply("ComposeConventionPlugin")
            pluginManager.apply("HotReloadConventionPlugin")
            pluginManager.apply("KspConventionPlugin")
            pluginManager.apply("RoomConventionPlugin")
            pluginManager.apply("DetektConventionPlugin")
        }
    }
}

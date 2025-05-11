import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Convention plugin for Hot Reload configuration.
 * This plugin applies the Hot Reload plugin.
 */
class HotReloadConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply required plugins
            pluginManager.apply("org.jetbrains.kotlin.plugin.hotreload")
        }
    }
}
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Convention plugin for Compose Multiplatform configuration.
 * This plugin applies the Compose Compiler and Compose Multiplatform plugins.
 */
class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply required plugins
            pluginManager.apply("org.jetbrains.compose")
            pluginManager.apply("org.jetbrains.compose.compiler")
        }
    }
}
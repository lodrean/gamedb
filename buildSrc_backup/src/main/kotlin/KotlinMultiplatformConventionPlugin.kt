import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Convention plugin for Kotlin Multiplatform configuration.
 * This plugin applies the Kotlin Multiplatform plugin and Kotlin Serialization plugin.
 */
class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply required plugins
            pluginManager.apply("org.jetbrains.kotlin.multiplatform")
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")
        }
    }
}

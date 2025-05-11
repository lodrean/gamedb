import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Convention plugin for KSP (Kotlin Symbol Processing) configuration.
 * This plugin applies the KSP plugin.
 */
class KspConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply required plugins
            pluginManager.apply("com.google.devtools.ksp")
        }
    }
}
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Convention plugin for Android Application configuration.
 * This plugin applies the Android Application plugin.
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply required plugins
            pluginManager.apply("com.android.application")
        }
    }
}
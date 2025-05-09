import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")
            
            // Configure detekt for all subprojects
            dependencies {
                "detektPlugins"("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.4")
                "detektPlugins"("com.twitter.compose.rules:detekt:0.0.26")
            }

            // Configure Detekt tasks
            tasks.withType<Detekt>().configureEach {
                config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
                baseline.set(file("$rootDir/config/detekt/baseline.xml"))
                parallel = true
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                    txt.required.set(false)
                    sarif.required.set(true)
                    md.required.set(false)
                }
            }

            // Configure baseline creation task
            tasks.withType<DetektCreateBaselineTask>().configureEach {
                config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
                baseline.set(file("$rootDir/config/detekt/baseline.xml"))
            }

            // Register a task to run Detekt on all sources
            tasks.register<Detekt>("detektAll") {
                description = "Runs Detekt on all sources"
                config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
                baseline.set(file("$rootDir/config/detekt/baseline.xml"))
                setSource(files(projectDir))
                include("**/*.kt")
                include("**/*.kts")
                exclude("**/resources/**")
                exclude("**/build/**")
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                    txt.required.set(false)
                    sarif.required.set(true)
                    md.required.set(false)
                }
            }
        }
    }
}
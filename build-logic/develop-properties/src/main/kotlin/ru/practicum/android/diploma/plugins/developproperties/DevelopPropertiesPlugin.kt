package ru.practicum.android.diploma.plugins.developproperties

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.StringReader
import java.util.*

/**
 * Хранилище значений, прочитанных из файла develop.properties
 * Плагин дает возможность получить к ним типизированный доступ в build.gradle.kts
 */

class DevelopPropertiesPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            val developProperties = extensions.create("developProperties", DevelopProperties::class.java)
            val properties = readProperties(project)

            properties?.let { writeProperties(it, developProperties) }
        }
    }

    private fun readProperties(project: Project): Properties? {
        with(project) {
            val fileContent = providers
                .fileContents(rootProject.layout.projectDirectory.file("develop.properties"))
                .asText
                .orNull

            return fileContent?.let { content ->
                Properties().apply { load(StringReader(content)) }
            }
        }
    }

    private fun writeProperties(
        properties: Properties,
        developProperties: DevelopProperties,
    ) {
        with(developProperties) {
            properties.getProperty("hhAccessToken")?.let {
                hhAccessToken = it
            }
        }
    }

}

package ru.practicum.android.diploma.data.detail

import ru.practicum.android.diploma.data.dto.FullVacancyDto
import ru.practicum.android.diploma.data.dto.Language
import ru.practicum.android.diploma.data.dto.License
import ru.practicum.android.diploma.data.dto.SkillName
import ru.practicum.android.diploma.domain.models.detail.FullVacancy
import ru.practicum.android.diploma.util.toBulletedList

class Mapper {
    fun toFullVacancy(fullVacancyDto: FullVacancyDto): FullVacancy {
        return FullVacancy(
            fullVacancyDto.id,
            fullVacancyDto.name,
            fullVacancyDto.address.city,
            fullVacancyDto.contacts,
            fullVacancyDto.description,
            fullVacancyDto.employer,
            fullVacancyDto.experience?.name.toString(),
            fullVacancyDto.salary,
            contentMap(fullVacancyDto.keySkills).toBulletedList(),
            addRequirements(fullVacancyDto).toString(),
            )
    }

    private fun addRequirements(fullVacancyDto: FullVacancyDto): List<String> {
        val requirements = mutableListOf<String>()
        if (fullVacancyDto.driverLicense.isNotEmpty()) {
            requirements.add("Права \n")
            requirements.add(contentMap(fullVacancyDto.driverLicense).toBulletedList().toString())
        }
        if (fullVacancyDto.languages.isNotEmpty()) {
            requirements.add("Языки \n")
            requirements.add(contentMap(fullVacancyDto.languages).toBulletedList().toString())
        }
        return requirements
    }

    private fun <T> contentMap(listDto: List<T>): List<String> {
        val content = mutableListOf<String>()
        for (i in listDto.indices) {
            val q = listDto[i]
            when (q) {
                is SkillName -> content.add(q.name.toString())
                is Language -> content.add(q.name.toString())
                is License -> content.add(q.id.toString())
            }
        }
        return content
    }
}



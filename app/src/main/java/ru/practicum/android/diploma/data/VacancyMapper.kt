package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.FullVacancyDto
import ru.practicum.android.diploma.data.dto.Language
import ru.practicum.android.diploma.data.dto.License
import ru.practicum.android.diploma.data.dto.SkillName
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.detail.FullVacancy
import ru.practicum.android.diploma.util.toBulletedList

class VacancyMapper {
    fun mapVacancyFromDto(vacancyDto: VacancyDto, foundValue: Int): Vacancy {
        return Vacancy(
            vacancyDto.id,
            vacancyDto.name,
            vacancyDto.area.name,
            vacancyDto.employer.name,
            found = foundValue,
            vacancyDto.employer.logo_urls?.original,
            Salary(
                vacancyDto.salary?.currency,
                vacancyDto.salary?.from,
                vacancyDto.salary?.to
            ),
        )
    }

    fun mapVacancyFromFullVacancyDto(vacancyDto: FullVacancyDto): FullVacancy {
        return FullVacancy(
            vacancyDto.id,
            vacancyDto.name,
            vacancyDto.area.name,
            vacancyDto.employer?.name ?: "",
            vacancyDto.employer?.logo_urls?.original ?: "",
            Salary(
                vacancyDto.salary?.currency,
                vacancyDto.salary?.from,
                vacancyDto.salary?.to
            ),
            vacancyDto.brandedDescription,
            vacancyDto.contacts,
            vacancyDto.description,
            vacancyDto.experience?.name.toString(),
            vacancyDto.employment?.name.toString(),
            if (vacancyDto.keySkills != null) contentMap(vacancyDto.keySkills).toBulletedList() else "",
            addRequirements(vacancyDto)
        )
    }


    private fun addRequirements(fullVacancyDto: FullVacancyDto): String {
        var requirements = ""
        if (!fullVacancyDto.driverLicense.isNullOrEmpty()) {
            requirements += "Права категории"
            //requirements += contentMap(fullVacancyDto.driverLicense)
            requirements += "\n"
        }
        if (!fullVacancyDto.languages.isNullOrEmpty()) {
            requirements += "Знание язаков: "
            //requirements += contentMap(fullVacancyDto.languages)
            requirements += "\n"

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

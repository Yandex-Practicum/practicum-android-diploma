package ru.practicum.android.diploma.data.detail

import ru.practicum.android.diploma.data.dto.FullVacancyDto
import ru.practicum.android.diploma.data.dto.SkillName
import ru.practicum.android.diploma.domain.models.detail.Vacancy
import ru.practicum.android.diploma.util.toBulletedList

class Mapper {
    fun toFullVacancy(fullVacancyDto: FullVacancyDto): Vacancy {
        return Vacancy(
            fullVacancyDto.name,
            fullVacancyDto.address.city,
            fullVacancyDto.brandedDescription,
            fullVacancyDto.contacts,
            fullVacancyDto.description,
            fullVacancyDto.employer,
            fullVacancyDto.experience?.name.toString(),
            fullVacancyDto.salary,
            skillsMap(fullVacancyDto.keySkills).toBulletedList(),
        )
    }

    private fun skillsMap(listDto: List<SkillName>): List<String> {
        val skills = mutableListOf<String>()
        for (i in listDto.indices) {
            skills.add(listDto[i].name.toString())
        }
        return skills
    }

}



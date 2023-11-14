package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.Language
import ru.practicum.android.diploma.data.dto.License
import ru.practicum.android.diploma.data.dto.SkillName
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.detail.FullVacancyDto
import ru.practicum.android.diploma.domain.models.Contact
import ru.practicum.android.diploma.domain.models.Phone
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
            vacancyDto.alternate_url,
            vacancyDto.brandedDescription,
            Contact(
                vacancyDto.contacts?.name,
                vacancyDto.contacts?.email,
                phoneFromDto(vacancyDto.contacts?.phones)
            ),
            vacancyDto.description,
            vacancyDto.experience?.name.toString(),
            vacancyDto.employment?.name.toString(),
            if (vacancyDto.keySkills != null) contentMap(vacancyDto.keySkills).toBulletedList() else "",
        )
    }

    private fun phoneFromDto(phones: List<ru.practicum.android.diploma.data.dto.Phone>?): List<Phone> {
        return if (!phones.isNullOrEmpty()) phones.map { mapPhones(it) } else mutableListOf()
    }

    private fun mapPhones(phoneDto: ru.practicum.android.diploma.data.dto.Phone): Phone {
        return Phone(
            phoneDto.city,
            phoneDto.comment,
            phoneDto.country,
            phoneDto.number
        )
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
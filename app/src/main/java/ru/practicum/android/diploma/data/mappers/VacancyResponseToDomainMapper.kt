package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.Vacancy
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.domain.models.Vacancy as DomainVacancy

class VacancyResponseToDomainMapper {

    fun map(vacancies: List<Vacancy>): List<DomainVacancy> {
        return vacancies.map {
            DomainVacancy(
                vacancyId = it.id,
                name = it.name,
                city = it.area.name,
                area = it.area.name,
                salaryFrom = it.salaryFrom,
                salaryTo = it.salaryTo,
                salaryCurrency = it.currency,
                employerName = it.employerName,
                employerLogoUrl = null,
                experience = null,
                employment = null,
                schedule = null,
                description = "",
                skills = emptyList(),
                contactEmail = null,
                contactName = null,
                contactPhoneNumbers = emptyList(),
                contactComment = emptyList(),
                isFavorite = null
            )
        }
    }

    fun map(vacancyDetails: VacancyDetails, isFavorite: Boolean): DomainVacancy {
        return DomainVacancy(
            vacancyId = vacancyDetails.id,
            name = vacancyDetails.name,
            city = vacancyDetails.area.name,
            area = vacancyDetails.area.name,
            salaryFrom = vacancyDetails.salaryFrom,
            salaryTo = vacancyDetails.salaryTo,
            salaryCurrency = vacancyDetails.currency,
            employerName = vacancyDetails.employerName,
            employerLogoUrl = null,
            experience = null,
            employment = null,
            schedule = null,
            description = vacancyDetails.description ?: "",
            skills = vacancyDetails.keySkills?.map { it.name } ?: emptyList(),
            contactEmail = null,
            contactName = null,
            contactPhoneNumbers = emptyList(),
            contactComment = emptyList(),
            isFavorite = isFavorite
        )
    }

}

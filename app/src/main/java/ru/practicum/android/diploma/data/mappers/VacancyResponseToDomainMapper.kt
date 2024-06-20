package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.Vacancy
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.domain.search.models.DomainVacancy as DomainVacancy

class VacancyResponseToDomainMapper {

    fun map(vacancies: List<Vacancy>): List<DomainVacancy> {
        return vacancies.map {
            DomainVacancy(
                vacancyId = it.id,
                name = it.name,
                city = it.area.name,
                area = it.area.name,
                salaryFrom = it.salary?.from,
                salaryTo = it.salary?.to,
                salaryCurrency = it.salary?.currency,
                employerName = it.employer.name,
                employerLogoUrl = it.employer.logoUrls?.original,
                experience = null,
                employment = null,
                schedule = null,
                description = "",
                skills = emptyList(),
                contactEmail = null,
                contactName = null,
                contactPhoneNumbers = emptyList(),
                contactComment = emptyList(),
                url = it.url,
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
            salaryFrom = vacancyDetails.salary?.from,
            salaryTo = vacancyDetails.salary?.to,
            salaryCurrency = vacancyDetails.salary?.currency,
            employerName = vacancyDetails.employer.name,
            employerLogoUrl = vacancyDetails.employer.logoUrls?.original,
            experience = vacancyDetails.experience?.name,
            employment = null,
            schedule = null,
            description = vacancyDetails.description ?: "",
            skills = vacancyDetails.keySkills?.map { it.name } ?: emptyList(),
            contactEmail = vacancyDetails.contacts?.email,
            contactName = vacancyDetails.contacts?.name,
            contactPhoneNumbers = emptyList(),
            contactComment = emptyList(),
            url = vacancyDetails.url,
            isFavorite = isFavorite
        )
    }

}

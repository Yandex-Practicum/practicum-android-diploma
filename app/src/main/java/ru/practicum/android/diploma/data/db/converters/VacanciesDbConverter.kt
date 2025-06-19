package ru.practicum.android.diploma.data.db.converters

import ru.practicum.android.diploma.data.db.entity.VacanciesEntity
import ru.practicum.android.diploma.domain.models.VacancyDetails
import java.util.Date

class VacanciesDbConverter {
    fun map(vacancy: VacancyDetails, dateAdd: Date): VacanciesEntity {
        return VacanciesEntity(
            id = vacancy.id,
            name = vacancy.name,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurr = vacancy.salaryCurr,
            areaName = vacancy.areaName,
            employerName = vacancy.employerName,
            employerLogoUrl = vacancy.employerUrls,
            keySkills = vacancy.keySkills,
            employmentForm = vacancy.employmentForm,
            professionalRoles = vacancy.professionalRoles,
            experience = vacancy.experience,
            description = vacancy.description,
            schedule = vacancy.schedule,
            address = vacancy.address,
            dateAdd = dateAdd,
            url = vacancy.url,
            employment = vacancy.employment,
        )
    }

    fun map(vacanciesEntity: VacanciesEntity): VacancyDetails {
        return VacancyDetails(
            id = vacanciesEntity.id,
            name = vacanciesEntity.name,
            areaName = vacanciesEntity.areaName,
            employerName = vacanciesEntity.employerName ?: "",
            employerUrls = vacanciesEntity.employerLogoUrl,
            salaryFrom = vacanciesEntity.salaryFrom,
            salaryTo = vacanciesEntity.salaryTo,
            salaryCurr = vacanciesEntity.salaryCurr,
            keySkills = vacanciesEntity.keySkills,
            employmentForm = vacanciesEntity.employmentForm,
            professionalRoles = vacanciesEntity.professionalRoles,
            experience = vacanciesEntity.experience,
            description = vacanciesEntity.description,
            schedule = vacanciesEntity.schedule,
            address = vacanciesEntity.address,
            employment = vacanciesEntity.employment,
            isFavorite = true,
            url = vacanciesEntity.url
        )
    }
}

package ru.practicum.android.diploma.data.db.converters

import ru.practicum.android.diploma.data.db.entity.VacanciesEntity
import ru.practicum.android.diploma.domain.models.VacancyDetail
import java.util.Date

class VacanciesDbConverter {
    fun map(vacancy: VacancyDetail): VacanciesEntity {
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
            dateAdd = Date()
        )
    }

    fun map(vacanciesEntity: VacanciesEntity): VacancyDetail {
        return VacancyDetail(
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
        )
    }
}

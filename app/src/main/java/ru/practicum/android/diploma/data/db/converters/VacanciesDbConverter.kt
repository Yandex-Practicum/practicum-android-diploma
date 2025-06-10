package ru.practicum.android.diploma.data.db.converters

import ru.practicum.android.diploma.data.db.entity.VacanciesEntity
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail
import java.util.Date

class VacanciesDbConverter {
    fun map(vacancy: VacancyDetail): VacanciesEntity {
        return VacanciesEntity(
            id = vacancy.id,
            name = "Не пыльная работа",
            salaryFrom = 102_000,
            salaryTo = null,
            salaryCurr = "RUR",
            areaName = "Москва",
            employerName = "",
            employerLogoUrl = null,
            keySkills = listOf("уметь фсё"),
            employmentForm = "нет",
            professionalRoles = listOf("Уборка помещений", "Протирать окна"),
            experience = "",
            description = "Описание",
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
            salaryCurr = vacanciesEntity.salaryCurr ?: "RUR",
            keySkills = vacanciesEntity.keySkills,
            employmentForm = vacanciesEntity.employmentForm,
            professionalRoles = vacanciesEntity.professionalRoles,
            experience = vacanciesEntity.experience,
            description = vacanciesEntity.description,
        )
    }
}

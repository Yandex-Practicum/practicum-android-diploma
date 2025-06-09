package ru.practicum.android.diploma.data.db.converters

import ru.practicum.android.diploma.data.db.entity.VacanciesEntity
import ru.practicum.android.diploma.domain.models.Vacancy
import java.util.Date

/**
 * Пока не готова модель Vacancy, тут просто для примера
 */

class VacanciesDbConverter {
    fun map(vacancy: Vacancy): VacanciesEntity {
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
            employmentForm = listOf("нет"),
            professionalRoles = listOf("Уборка помещений", "Протирать окна"),
            experience = "",
            description = "Описание",
            dateAdd = Date()
        )
    }

    fun map(vacanciesEntity: VacanciesEntity): Vacancy {
        return Vacancy(
            id = vacanciesEntity.id
        )
    }
}

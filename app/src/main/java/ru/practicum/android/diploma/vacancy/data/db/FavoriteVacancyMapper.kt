package ru.practicum.android.diploma.vacancy.data.db

import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

object FavoriteVacancyMapper {
    fun map(entity: FavoriteVacancyEntity): VacancyDetails {
        return VacancyDetails(
            id = entity.id,
            name = entity.name,
            employerName = entity.employerName,
            employerLogoUrl = entity.logoUrl,
            area = entity.areaName,
            salary = formatSalary(entity.salaryFrom, entity.salaryTo, entity.currency),
            experience = entity.experience,
            employment = entity.employment,
            schedule = entity.schedule,
            description = entity.descriptionHtml ?: "",
            keySkills = entity.keySkills?.split(',') ?: emptyList(),
            url = entity.url
        )
    }

    private fun formatSalary(from: Int?, to: Int?, currency: String?): String {
        if (from == null && to == null) return "Зарплата не указана"
        val curr = currency ?: ""
        return when {
            from != null && to != null -> "от $from до $to $curr"
            from != null -> "от $from $curr"
            to != null -> "до $to $curr"
            else -> "Зарплата не указана"
        }
    }
}

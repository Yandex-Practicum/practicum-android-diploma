package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.search.domain.model.VacancyPreview
import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity

object VacancyPreviewMapper {

    fun map(entity: FavoriteVacancyEntity): VacancyPreview {
        return VacancyPreview(
            id = entity.id.toIntOrNull() ?: 0,
            name = entity.name,
            employerName = entity.employerName ?: "Работодатель не указан",
            from = entity.salaryFrom,
            to = entity.salaryTo,
            currency = entity.currency,
            url = entity.logoUrl
        )
    }
}

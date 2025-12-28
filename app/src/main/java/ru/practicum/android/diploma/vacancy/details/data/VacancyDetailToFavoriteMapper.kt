package ru.practicum.android.diploma.vacancy.details.data

import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

class VacancyDetailToFavoriteMapper {
    fun VacancyDetail.toFavoriteVacancyEntity(): FavoriteVacancyEntity {
        return FavoriteVacancyEntity(
            id = id,
            name = name,
            description = description,
            salaryFrom = salary?.from,
            salaryTo = salary?.to,
            currency = salary?.currency,
            city = address?.city,
            street = address?.street,
            building = address?.building,
            fullAddress = address?.fullAddress,
            experienceId = experience?.id,
            experienceName = experience?.name,
            employmentId = employment?.id,
            employmentName = employment?.name,
            scheduleId = schedule?.id,
            scheduleName = schedule?.name,
            employerId = employer.id,
            employerName = employer.name,
            employerLogoUrl = employer.logo,
            areaId = area.id,
            areaName = area.name,
            industryId = industry.id,
            industryName = industry.name,
            skills = skills,
            url = url,
            addedAt = System.currentTimeMillis()
        )
    }
}

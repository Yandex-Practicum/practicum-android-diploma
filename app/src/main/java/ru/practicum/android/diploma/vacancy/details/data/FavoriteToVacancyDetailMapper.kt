package ru.practicum.android.diploma.vacancy.details.data

import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.search.domain.model.Address
import ru.practicum.android.diploma.search.domain.model.Contacts
import ru.practicum.android.diploma.search.domain.model.Employer
import ru.practicum.android.diploma.search.domain.model.Employment
import ru.practicum.android.diploma.search.domain.model.Experience
import ru.practicum.android.diploma.search.domain.model.FilterArea
import ru.practicum.android.diploma.search.domain.model.FilterIndustry
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.Schedule
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

class FavoriteToVacancyDetailMapper {
    fun FavoriteVacancyEntity.toVacancyDetail(): VacancyDetail {
        return VacancyDetail(
            id = id,
            name = name,
            description = description,
            salary = if (salaryFrom != null || salaryTo != null || currency != null) {
                Salary(
                    from = salaryFrom,
                    to = salaryTo,
                    currency = currency
                )
            } else {
                null
            },
            address = if (city != null || street != null || building != null || fullAddress != null) {
                Address(
                    city = city ?: "",
                    street = street ?: "",
                    building = building ?: "",
                    fullAddress = fullAddress ?: ""
                )
            } else {
                null
            },
            experience = if (experienceId != null && experienceName != null) {
                Experience(
                    id = experienceId!!,
                    name = experienceName!!
                )
            } else {
                null
            },
            schedule = if (scheduleId != null && scheduleName != null) {
                Schedule(
                    id = scheduleId!!,
                    name = scheduleName!!
                )
            } else {
                null
            },
            employment = if (employmentId != null && employmentName != null) {
                Employment(
                    id = employmentId!!,
                    name = employmentName!!
                )
            } else {
                null
            },
            // нужно проверить сохранение Contacts в БД, пока пустые поля
            contacts = Contacts(
                id = "",
                name = "",
                email = "",
                phone = emptyList()
            ),
            employer = Employer(
                id = employerId,
                name = employerName,
                logo = employerLogoUrl ?: ""
            ),
            area = FilterArea(
                id = areaId ?: 0,
                name = areaName ?: "",
                parentId = null,
                areas = emptyList()
            ),
            skills = skills,
            url = url,
            industry = FilterIndustry(
                id = industryId ?: 0,
                name = industryName ?: ""
            )
        )
    }
}

package ru.practicum.android.diploma.vacancy.data.db

import ru.practicum.android.diploma.util.VacancyFormatter
import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsDto
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

    fun mapDtoToDomain(dto: VacancyDetailsDto): VacancyDetails {
        return VacancyDetails(
            id = dto.id,
            name = dto.name,
            employerName = dto.employer?.name,
            employerLogoUrl = dto.employer?.logoUrls?.original,
            area = dto.area?.name,
            salary = VacancyFormatter.formatSalary(dto.salary?.from, dto.salary?.to, dto.salary?.currency),
            experience = dto.experience?.name,
            employment = dto.employment?.name,
            schedule = dto.schedule?.name,
            description = dto.description ?: "",
            keySkills = dto.keySkills?.map { it.name } ?: emptyList(),
            url = dto.alternateUrl
        )
    }

    fun mapDtoToEntity(dto: VacancyDetailsDto): FavoriteVacancyEntity {
        return FavoriteVacancyEntity(
            id = dto.id,
            name = dto.name,
            employerName = dto.employer?.name,
            areaName = dto.area?.name,
            descriptionHtml = dto.description,
            salaryFrom = dto.salary?.from,
            salaryTo = dto.salary?.to,
            currency = dto.salary?.currency,
            experience = dto.experience?.name,
            employment = dto.employment?.name,
            schedule = dto.schedule?.name,
            keySkills = dto.keySkills?.joinToString(","),
            logoUrl = dto.employer?.logoUrls?.original,
            url = dto.alternateUrl
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

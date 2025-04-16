package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.db.FavVacancyEntity
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.KeySkill
import ru.practicum.android.diploma.domain.models.LogoUrls
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyEntityMapper {
    private fun convertToVacancy(entity: FavVacancyEntity): Vacancy {
        val employer = Employer(
            id = entity.employerId,
            name = entity.employerName,
            logoUrls = LogoUrls(original = entity.employerLogoUrl)
        )
        val salary = Salary(
            currency = entity.salaryCurrency.ifEmpty { null },
            from = if (entity.salaryFrom == 0) null else entity.salaryFrom,
            gross = entity.salaryGross,
            to = entity.salaryTo.ifEmpty { null }
        )
        return Vacancy(
            id = entity.id,
            name = entity.name,
            employer = employer,
            salary = salary,
            salaryRange = null
        )
    }

    fun convertToVacancyDetails(entity: FavVacancyEntity): VacancyDetails {
        val vacancy = convertToVacancy(entity)
        val area = Area(id = entity.areaId, name = entity.areaName)
        val experience = Experience(id = entity.experienceId, name = entity.experienceName)
        val keySkills = entity.keySkills.split(",").map { KeySkill(it) }

        return VacancyDetails(
            id = vacancy.id,
            name = vacancy.name,
            area = area,
            description = entity.description,
            employer = vacancy.employer,
            keySkills = ArrayList(keySkills),
            salary = vacancy.salary,
            salaryRange = vacancy.salaryRange,
            experience = experience
        )
    }

    @Suppress("CyclomaticComplexMethod")
    fun convertFromVacancyDetails(vacancy: VacancyDetails): FavVacancyEntity {
        val salary = if (vacancy.salary != null) {
            Salary(
                currency = vacancy.salary.currency ?: "",
                from = vacancy.salary.from ?: 0,
                gross = vacancy.salary.gross ?: false,
                to = vacancy.salary.to ?: ""
            )
        } else {
            Salary(
                currency = vacancy.salaryRange?.currency ?: "",
                from = vacancy.salaryRange?.from ?: 0,
                gross = vacancy.salaryRange?.gross ?: false,
                to = vacancy.salaryRange?.to ?: ""
            )
        }

        return FavVacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            areaId = vacancy.area?.id ?: "",
            areaName = vacancy.area?.name ?: "",
            description = vacancy.description ?: "",
            employerId = vacancy.employer?.id ?: "",
            employerName = vacancy.employer?.name ?: "",
            employerLogoUrl = vacancy.employer?.logoUrls?.original ?: "",
            keySkills = vacancy.keySkills.joinToString(","),
            salaryFrom = salary.from ?: 0,
            salaryGross = salary.gross ?: false,
            salaryTo = salary.to ?: "",
            salaryCurrency = salary.currency ?: "",
            experienceId = vacancy.experience?.id ?: "",
            experienceName = vacancy.experience?.name ?: ""
        )
    }
}

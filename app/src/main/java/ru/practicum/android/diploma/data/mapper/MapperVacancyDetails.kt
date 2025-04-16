package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.ExperienceDto
import ru.practicum.android.diploma.data.dto.LogoUrlsDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.KeySkill
import ru.practicum.android.diploma.domain.models.LogoUrls
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacancyDetails

class MapperVacancyDetails {
    fun map(vacancyDetails: VacancyDetailsDto): VacancyDetails {
        return VacancyDetails(
            id = vacancyDetails.id,
            name = vacancyDetails.name,
            area = mapArea(vacancyDetails.area),
            description = vacancyDetails.description,
            employer = vacancyDetails.employer?.let { mapEmployer(it) },
            keySkills = vacancyDetails.keySkills.map { KeySkill(name = it.name) },
            salary = vacancyDetails.salary?.let { mapSalary(it) },
            salaryRange = vacancyDetails.salaryRange?.let { mapSalary(it) },
            experience = vacancyDetails.experience?.let { mapExperience(it) }
        )
    }

    private fun mapArea(area: AreaDto?): Area {
        return Area(id = area?.id, name = area?.name, parentId = area?.parentId)
    }

    private fun mapLogoUrls(logoUrls: LogoUrlsDto?): LogoUrls {
        return LogoUrls(size90 = logoUrls?.size90, size240 = logoUrls?.size240, original = logoUrls?.original)
    }

    private fun mapEmployer(employer: EmployerDto): Employer {
        return Employer(id = employer.id, logoUrls = mapLogoUrls(employer.logoUrls), name = employer.name)
    }

    private fun mapSalary(salary: SalaryDto): Salary {
        return Salary(currency = salary.currency, from = salary.from, gross = salary.gross, to = salary.to)
    }

    private fun mapExperience(experience: ExperienceDto): Experience {
        return Experience(id = experience.id, name = experience.name)
    }
}

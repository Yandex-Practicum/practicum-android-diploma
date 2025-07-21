package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.models.areas.AreasResponseDto
import ru.practicum.android.diploma.data.models.industries.IndustryDto
import ru.practicum.android.diploma.data.models.storage.FilterParametersDto
import ru.practicum.android.diploma.data.models.vacancies.SalaryRangeDto
import ru.practicum.android.diploma.data.models.vacancies.VacanciesDto
import ru.practicum.android.diploma.data.models.vacancydetails.EmploymentFormDto
import ru.practicum.android.diploma.data.models.vacancydetails.VacancyDetailsResponseDto
import ru.practicum.android.diploma.data.models.vacancydetails.WorkFormatDto
import ru.practicum.android.diploma.domain.models.filters.Region
import ru.practicum.android.diploma.domain.models.filters.Country
import ru.practicum.android.diploma.domain.models.filters.FilterParameters
import ru.practicum.android.diploma.domain.models.filters.Industry
import ru.practicum.android.diploma.domain.models.salary.Salary
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy
import ru.practicum.android.diploma.domain.models.vacancydetails.EmploymentForm
import ru.practicum.android.diploma.domain.models.vacancydetails.EmploymentFormType
import ru.practicum.android.diploma.domain.models.vacancydetails.VacancyDetails

fun VacanciesDto.toDomain(): Vacancy {
    return Vacancy(
        nameVacancy = name,
        alternateUrl = alternateUrl,
        id = id,
        employerName = employer?.name,
        logo = employer?.logoUrls?.logo90,
        salary = salaryRange.toDomain(),
        city = address?.city
    )
}

fun VacancyDetailsResponseDto.toDomain(): VacancyDetails {
    return VacancyDetails(
        id = id,
        name = name,
        salary = salaryRange.toDomain(),
        employer = employer?.name,
        experience = experience?.name,
        employmentForm = employmentForm.toDomain(),
        description = description,
        workFormat = workFormat?.mapNotNull { it.toDomain() },
        alternateUrl = alternateUrl,
        keySkills = keySkills?.map { it.name } ?: listOf(),
        city = area.name,
        logoUrl = employer?.logoUrls?.logo90
    )
}

fun VacancyDetails.toVacancy(): Vacancy {
    return Vacancy(
        id = id,
        alternateUrl = alternateUrl,
        nameVacancy = name,
        employerName = employer,
        logo = logoUrl,
        salary = salary,
        city = city,
        timestamp = System.currentTimeMillis()
    )
}

fun AreasResponseDto.toDomain(): Country {
    return Country(
        id = id,
        name = name,
        parentId = parentId,
    )
}

fun IndustryDto.toDomain(): Industry {
    return Industry(
        id = id,
        name = name
    )
}

fun FilterParametersDto.toDomain(): FilterParameters {
    return FilterParameters(
        countryName = countryName,
        countryId = countryId,
        regionName = regionName,
        industryId = industryId,
        industryName = industryName,
        salary = salary,
        checkboxWithoutSalary = checkboxWithoutSalary
    )
}

fun FilterParameters.toDto(): FilterParametersDto {
    return FilterParametersDto(
        countryName = countryName,
        countryId = countryId,
        regionName = regionName,
        industryId = industryId,
        industryName = industryName,
        salary = salary,
        checkboxWithoutSalary = checkboxWithoutSalary
    )
}

private fun SalaryRangeDto?.toDomain(): Salary {
    if (this == null) return Salary.NotSpecifies

    val from = this.from
    val to = this.to
    val currency = this.currency

    return when {
        from == null -> Salary.NotSpecifies
        to == null -> Salary.From(from, currency)
        to == from -> Salary.Fixed(from, currency)
        else -> Salary.Range(from, to, currency)
    }
}

fun EmploymentFormDto?.toDomain(): EmploymentForm? {
    if (this == null || name.isNullOrBlank()) return null

    val type = EmploymentFormType.fromId(this.id)
    return EmploymentForm(
        name = this.name,
        requiresSuffix = type?.requiresSuffix == true
    )
}

fun WorkFormatDto?.toDomain(): String? {
    return this?.name?.takeIf { it.isNotBlank() }
}

fun AreasResponseDto.toRegion(): Region {
    return Region(
        id = this.id,
        name = this.name,
        countryName = this.countryName ?: "",
        regionId = this.parentId,
        countryId = this.parentId
    )
}

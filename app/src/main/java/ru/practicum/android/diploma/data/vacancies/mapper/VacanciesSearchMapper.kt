package ru.practicum.android.diploma.data.vacancies.mapper

import ru.practicum.android.diploma.data.vacancies.dto.VacanciesSearchDtoResponse
import ru.practicum.android.diploma.data.vacancies.dto.list.DepartmentDto
import ru.practicum.android.diploma.data.vacancies.dto.list.EmployerDto
import ru.practicum.android.diploma.data.vacancies.dto.list.LogoUrlsDto
import ru.practicum.android.diploma.data.vacancies.dto.list.SalaryDto
import ru.practicum.android.diploma.data.vacancies.dto.list.VacancyAreaDto
import ru.practicum.android.diploma.data.vacancies.dto.list.VacancyDto
import ru.practicum.android.diploma.data.vacancies.dto.list.VacancyTypeDto
import ru.practicum.android.diploma.domain.models.vacacy.Department
import ru.practicum.android.diploma.domain.models.vacacy.Employer
import ru.practicum.android.diploma.domain.models.vacacy.LogoUrls
import ru.practicum.android.diploma.domain.models.vacacy.Salary
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.domain.models.vacacy.VacancyArea
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse
import ru.practicum.android.diploma.domain.models.vacacy.VacancyType


object VacanciesSearchMapper {
    fun map(vacancy: VacanciesSearchDtoResponse): VacancyResponse {
        return VacancyResponse(
            items = vacancy.items?.map { map(it) } ?: emptyList(),
            found = vacancy.found,
            page = vacancy.page,
            pages = vacancy.pages,
            perPage = vacancy.perPage,
        )
    }

    private fun map(vacancyDto: VacancyDto): Vacancy {
        return Vacancy(
            id = vacancyDto.id,
            department = map(vacancyDto.department),
            name = vacancyDto.name,
            area = map(vacancyDto.area),
            employer = map(vacancyDto.employer),
            salary = map(vacancyDto.salary),
            type = map(vacancyDto.type),
        )
    }

    private fun map(departmentDto: DepartmentDto?): Department? {
        if (departmentDto == null) return null
        return Department(
            id = departmentDto.id,
            name = departmentDto.name,
        )
    }

    private fun map(vacancyAreaDto: VacancyAreaDto): VacancyArea {
        return VacancyArea(
            id = vacancyAreaDto.id,
            name = vacancyAreaDto.name,
            url = vacancyAreaDto.url
        )
    }

    private fun map(employerDto: EmployerDto): Employer {
        return Employer(
            id = employerDto.id,
            logoUrls = map(employerDto.logoUrls),
            name = employerDto.name,
            trusted = employerDto.trusted,
            vacanciesUrl = employerDto.vacanciesUrl
        )
    }

    private fun map(logoUrlsDto: LogoUrlsDto?): LogoUrls? {
        if (logoUrlsDto == null) return null
        return LogoUrls(
            art90 = logoUrlsDto.art90,
            art240 = logoUrlsDto.art240,
            original = logoUrlsDto.original,
        )
    }

    private fun map(salaryDto: SalaryDto?): Salary? {
        if (salaryDto == null) return null
        return Salary(
            currency = salaryDto.currency,
            from = salaryDto.from,
            gross = salaryDto.gross,
            to = salaryDto.to,
        )
    }

    private fun map(vacancyTypeDto: VacancyTypeDto): VacancyType {
        return VacancyType(
            id = vacancyTypeDto.id,
            name = vacancyTypeDto.name,
        )
    }
}

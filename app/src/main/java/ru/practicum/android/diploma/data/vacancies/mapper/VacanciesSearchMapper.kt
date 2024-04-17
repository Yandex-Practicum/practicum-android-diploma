package ru.practicum.android.diploma.data.vacancies.mapper

import ru.practicum.android.diploma.data.vacancies.details.dto.ContactsDto
import ru.practicum.android.diploma.data.vacancies.details.dto.EmployerDto
import ru.practicum.android.diploma.data.vacancies.details.dto.SalaryDto
import ru.practicum.android.diploma.data.vacancies.details.dto.VacancyAreaDto
import ru.practicum.android.diploma.data.vacancies.details.dto.VacancyTypeDto
import ru.practicum.android.diploma.data.vacancies.dto.VacanciesSearchDtoResponse
import ru.practicum.android.diploma.data.vacancies.dto.list.DepartmentDto
import ru.practicum.android.diploma.data.vacancies.dto.list.LogoUrlsDto
import ru.practicum.android.diploma.data.vacancies.dto.list.VacancyDto
import ru.practicum.android.diploma.domain.models.vacacy.Contacts
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
            items = vacancy.items?.map { mapVacancy(it) } ?: emptyList(),
            found = vacancy.found,
            page = vacancy.page,
            pages = vacancy.pages,
            perPage = vacancy.perPage,
        )
    }

    private fun mapVacancy(vacancyDto: VacancyDto): Vacancy {
        return Vacancy(
            id = vacancyDto.id,
            department = mapDepartment(vacancyDto.department),
            name = vacancyDto.name,
            area = mapArea(vacancyDto.area),
            employer = mapEmployee(vacancyDto.employer),
            salary = mapSalary(vacancyDto.salary),
            type = mapVacancyType(vacancyDto.type),
            contacts = mapContacts(vacancyDto.contacts),
        )
    }

    private fun mapDepartment(departmentDto: DepartmentDto?): Department? {
        if (departmentDto == null) return null
        return Department(
            id = departmentDto.id,
            name = departmentDto.name,
        )
    }

    private fun mapArea(vacancyAreaDto: VacancyAreaDto): VacancyArea {
        return VacancyArea(
            id = vacancyAreaDto.id,
            name = vacancyAreaDto.name,
            url = vacancyAreaDto.url
        )
    }

    private fun mapEmployee(employerDto: EmployerDto): Employer {
        return Employer(
            id = employerDto.id,
            logoUrls = mapLogo(employerDto.logoUrls),
            name = employerDto.name,
            trusted = employerDto.trusted,
            vacanciesUrl = employerDto.vacanciesUrl
        )
    }

    private fun mapLogo(logoUrlsDto: LogoUrlsDto?): LogoUrls? {
        if (logoUrlsDto == null) return null
        return LogoUrls(
            art90 = logoUrlsDto.art90,
            art240 = logoUrlsDto.art240,
            original = logoUrlsDto.original,
        )
    }

    private fun mapSalary(salaryDto: SalaryDto?): Salary? {
        if (salaryDto == null) return null
        return Salary(
            currency = salaryDto.currency,
            from = salaryDto.from,
            gross = salaryDto.gross,
            to = salaryDto.to,
        )
    }

    private fun mapVacancyType(vacancyTypeDto: VacancyTypeDto): VacancyType {
        return VacancyType(
            id = vacancyTypeDto.id,
            name = vacancyTypeDto.name,
        )
    }

    private fun mapContacts(contactsDto: ContactsDto?): Contacts? {
        if (contactsDto == null) return null
        return Contacts(
            email = contactsDto.email,
            name = contactsDto.name,
            if (!contactsDto.phones.isNullOrEmpty()) "+${contactsDto.phones?.get(0)?.country} (${contactsDto.phones?.get(0)?.city}) ${contactsDto.phones?.get(0)?.number}" else null,
            if (!contactsDto.phones.isNullOrEmpty()) contactsDto.phones?.get(0)?.comment else null
        )
    }
}

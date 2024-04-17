package ru.practicum.android.diploma.data.vacancies.mapper

import ru.practicum.android.diploma.data.vacancies.details.VacancyDetailDtoResponse
import ru.practicum.android.diploma.data.vacancies.details.dto.ContactsDto
import ru.practicum.android.diploma.data.vacancies.details.dto.EmployerDto
import ru.practicum.android.diploma.data.vacancies.details.dto.KeySkillVacancyDetail
import ru.practicum.android.diploma.data.vacancies.details.dto.SalaryDto
import ru.practicum.android.diploma.data.vacancies.dto.list.LogoUrlsDto
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.vacacy.Contacts
import ru.practicum.android.diploma.domain.models.vacacy.Employer
import ru.practicum.android.diploma.domain.models.vacacy.LogoUrls
import ru.practicum.android.diploma.domain.models.vacacy.Salary

object VacancyDetailsMapper {
    fun map(vacancy: VacancyDetailDtoResponse): VacancyDetails {
        return VacancyDetails(
            id = vacancy.id,
            name = vacancy.name,
            salary = mapSalary(vacancy.salary),
            employer = mapEmployee(vacancy.employer),
            city = vacancy.address?.city,
            fullAddress = vacancy.address?.raw,
            areaName = vacancy.area.name,
            experience = vacancy.experience?.name,
            employment = vacancy.employment?.name,
            description = vacancy.description,
            contacts = mapContacts(vacancy.contacts),
            link = vacancy.vacancyLink,
            keySkills = mapKeySkills(vacancy.keySkills)
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

    private fun mapEmployee(employerDto: EmployerDto?): Employer? {
        if (employerDto == null) return null
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

    private fun mapContacts(contactsDto: ContactsDto?): Contacts? {
        if (contactsDto == null) return null
        return Contacts(
            email = contactsDto.email,
            name = contactsDto.name,
            if (!contactsDto.phones.isNullOrEmpty()) {
                "+${contactsDto.phones?.get(0)?.country} (${contactsDto.phones?.get(0)?.city}) " +
                    "${contactsDto.phones?.get(0)?.number}"
            } else { null },
            if (!contactsDto.phones.isNullOrEmpty()) {
                contactsDto.phones?.get(0)?.comment
            } else { null }
        )
    }

    private fun mapKeySkills(keySkills: List<KeySkillVacancyDetail>): String {
        if (keySkills.isEmpty()) return ""
        var skillsList = ""
        for (skill in keySkills) {
            skillsList += "• " + skill.name + "\n"
        }
        return skillsList
    }
}

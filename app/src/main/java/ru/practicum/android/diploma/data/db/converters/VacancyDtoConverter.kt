package ru.practicum.android.diploma.data.db.converters

import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.KeySkillDto
import ru.practicum.android.diploma.data.dto.PhoneDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDtoConverter {
    fun map(vacancyDto: VacancyDto): Vacancy {
        return with(vacancyDto) {
            Vacancy(
                id = id,
                vacancyName = vacancyName,
                companyName = employer.name,
                alternateUrl = alternateUrl,
                logoUrl = employer.logo?.original,
                area = area.name,
                employment = employment?.name,
                experience = experience?.name,
                salary = createSalary(salary),
                description = description,
                keySkills = extractKeySkills(keySkills),
                contacts = createContacts(contacts),
                comment = comment,
                schedule = schedule?.name,
                address = address?.fullAddress
            )
        }
    }

    private fun createSalary(salaryDto: SalaryDto?): Salary? {
        return salaryDto?.let {
            Salary(
                currency = it.currency,
                from = it.from,
                gross = it.gross,
                to = it.to
            )
        }
    }

    private fun createContacts(contactsDto: ContactsDto?): Contacts? {
        return contactsDto?.let {
            Contacts(
                email = it.email,
                name = it.name,
                phones = it.phones?.map { createPhone(it) }
            )
        }
    }

    private fun createPhone(phoneDto: PhoneDto?): Phone? {
        return phoneDto?.let {
            Phone(
                city = it.city,
                comment = it.comment,
                country = it.country,
                number = it.number
            )
        }
    }

    private fun extractKeySkills(keySkills: List<KeySkillDto>?): List<String?> {
        return keySkills?.map { it.name } ?: emptyList()
    }
}

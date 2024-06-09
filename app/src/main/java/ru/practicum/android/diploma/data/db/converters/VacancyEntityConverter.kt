package ru.practicum.android.diploma.data.db.converters

import ru.practicum.android.diploma.data.db.ContactsEntity
import ru.practicum.android.diploma.data.db.PhoneEntity
import ru.practicum.android.diploma.data.db.SalaryEntity
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyEntityConverter {
    fun map(vacancyEntity: VacancyEntity): Vacancy {
        return with(vacancyEntity) {
            Vacancy(
                id = id,
                vacancyName = vacancyName,
                companyName = companyName,
                alternateUrl = alternateUrl,
                logoUrl = logoUrl,
                area = area,
                employment = employment,
                experience = experience,
                salary = createSalary(salary),
                description = description,
                keySkills = keySkills,
                contacts = createContacts(contacts),
                comment = comment,
                schedule = schedule,
                address = address
            )
        }
    }

    private fun createSalary(salaryEntity: SalaryEntity?): Salary? {
        return salaryEntity?.let {
            Salary(
                currency = it.currency,
                from = it.from,
                gross = it.gross,
                to = it.to
            )
        }
    }

    private fun createContacts(contactsEntity: ContactsEntity?): Contacts? {
        return contactsEntity?.let {
            Contacts(
                email = it.email,
                name = it.name,
                phones = it.phones?.map { createPhone(it) }
            )
        }
    }

    private fun createPhone(phoneEntity: PhoneEntity?): Phone? {
        return phoneEntity?.let {
            Phone(
                city = it.city,
                comment = it.comment,
                country = it.country,
                number = it.number
            )
        }
    }

    fun map(vacancy: Vacancy): VacancyEntity {
        return with(vacancy) {
            VacancyEntity(
                id = id,
                vacancyName = vacancyName,
                companyName = companyName,
                alternateUrl = alternateUrl,
                logoUrl = logoUrl,
                area = area,
                employment = employment,
                experience = experience,
                salary = createSalaryEntity(salary),
                description = description,
                keySkills = keySkills,
                contacts = createContactsEntity(contacts),
                comment = comment,
                schedule = schedule,
                address = address
            )
        }
    }

    private fun createSalaryEntity(salary: Salary?): SalaryEntity? {
        return salary?.let {
            SalaryEntity(
                currency = it.currency,
                from = it.from,
                gross = it.gross,
                to = it.to
            )
        }
    }

    private fun createContactsEntity(contacts: Contacts?): ContactsEntity? {
        return contacts?.let {
            ContactsEntity(
                email = it.email,
                name = it.name,
                phones = it.phones?.map { createPhoneEntity(it) }
            )
        }
    }

    private fun createPhoneEntity(phone: Phone?): PhoneEntity? {
        return phone?.let {
            PhoneEntity(
                city = it.city,
                comment = it.comment,
                country = it.country,
                number = it.number
            )
        }
    }
}

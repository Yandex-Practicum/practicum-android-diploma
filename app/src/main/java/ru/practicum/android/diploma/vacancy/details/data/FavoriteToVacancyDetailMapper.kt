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
            salary = mapSalary(),
            address = mapAddress(),
            experience = mapExperience(),
            schedule = mapSchedule(),
            employment = mapEmployment(),
            contacts = mapContacts(),
            employer = mapEmployer(),
            area = mapArea(),
            skills = skills,
            url = url,
            industry = mapIndustry()
        )
    }
}

private fun FavoriteVacancyEntity.mapSalary(): Salary? {
    if (salaryFrom == null && salaryTo == null && currency == null) return null
    return Salary(
        from = salaryFrom,
        to = salaryTo,
        currency = currency
    )
}

private fun FavoriteVacancyEntity.mapAddress(): Address? {
    if (listOf(city, street, building, fullAddress).all { it == null }) return null

    return Address(
        city = city.orEmpty(),
        street = street.orEmpty(),
        building = building.orEmpty(),
        fullAddress = fullAddress.orEmpty()
    )
}

private fun FavoriteVacancyEntity.mapExperience(): Experience? {
    return experienceId?.let { id ->
        experienceName?.let { name ->
            Experience(id, name)
        }
    }
}

private fun FavoriteVacancyEntity.mapSchedule(): Schedule? {
    return scheduleId?.let { id ->
        scheduleName?.let { name ->
            Schedule(id, name)
        }
    }
}

private fun FavoriteVacancyEntity.mapEmployment(): Employment? {
    return employmentId?.let { id ->
        employmentName?.let { name ->
            Employment(id, name)
        }
    }
}

private fun FavoriteVacancyEntity.mapContacts(): Contacts? {
    val isEmpty = listOf(
        contactId,
        contactName,
        contactEmail
    ).all { it.isNullOrBlank() } && contactPhones.isEmpty()

    if (isEmpty) return null

    return Contacts(
        id = contactId.orEmpty(),
        name = contactName.orEmpty(),
        email = contactEmail.orEmpty(),
        phone = contactPhones
    )
}

private fun FavoriteVacancyEntity.mapEmployer(): Employer {
    return Employer(
        id = employerId,
        name = employerName,
        logo = employerLogoUrl.orEmpty()
    )
}

private fun FavoriteVacancyEntity.mapArea(): FilterArea {
    return FilterArea(
        id = areaId ?: 0,
        name = areaName.orEmpty(),
        parentId = null,
        areas = emptyList()
    )
}

private fun FavoriteVacancyEntity.mapIndustry(): FilterIndustry {
    return FilterIndustry(
        id = industryId ?: 0,
        name = industryName.orEmpty()
    )
}

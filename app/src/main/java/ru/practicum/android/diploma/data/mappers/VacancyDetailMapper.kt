package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.AddressDto
import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.EmploymentDto
import ru.practicum.android.diploma.data.dto.ExperienceDto
import ru.practicum.android.diploma.data.dto.PhoneDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.ScheduleDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.domain.models.VacancyAddress
import ru.practicum.android.diploma.domain.models.VacancyContacts
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.models.VacancyEmployer
import ru.practicum.android.diploma.domain.models.VacancyEmployment
import ru.practicum.android.diploma.domain.models.VacancyExperience
import ru.practicum.android.diploma.domain.models.VacancyPhone
import ru.practicum.android.diploma.domain.models.VacancySalary
import ru.practicum.android.diploma.domain.models.VacancySchedule

fun VacancyDetailDto.toDomain(): VacancyDetail {
    return VacancyDetail(
        id = id,
        name = name,
        description = description.orEmpty(),
        salary = salary?.toDomain(),
        address = address?.toDomain(),
        experience = experience?.toDomain(),
        schedule = schedule?.toDomain(),
        employment = employment?.toDomain(),
        contacts = contacts?.toDomain(),
        employer = employer?.toDomain(),
        areaName = area?.name,
        skills = skills.orEmpty(),
        url = url.orEmpty(),
    )
}

private fun SalaryDto.toDomain(): VacancySalary {
    return VacancySalary(
        from = from,
        to = to,
        currency = currency,
    )
}

private fun AddressDto.toDomain(): VacancyAddress {
    return VacancyAddress(
        city = city,
        street = street,
        building = building,
        raw = raw,
    )
}

private fun ExperienceDto.toDomain(): VacancyExperience {
    return VacancyExperience(
        id = id,
        name = name,
    )
}

private fun ScheduleDto.toDomain(): VacancySchedule {
    return VacancySchedule(
        id = id,
        name = name,
    )
}

private fun EmploymentDto.toDomain(): VacancyEmployment {
    return VacancyEmployment(
        id = id,
        name = name,
    )
}

private fun ContactsDto.toDomain(): VacancyContacts {
    return VacancyContacts(
        name = name,
        email = email,
        phones = phones?.map(PhoneDto::toDomain).orEmpty(),
    )
}

private fun PhoneDto.toDomain(): VacancyPhone {
    return VacancyPhone(
        comment = comment,
        formatted = formatted,
    )
}

private fun EmployerDto.toDomain(): VacancyEmployer {
    return VacancyEmployer(
        name = name,
        logo = logo,
    )
}

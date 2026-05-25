package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.AddressDto
import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.EmploymentDto
import ru.practicum.android.diploma.data.dto.ExperienceDto
import ru.practicum.android.diploma.data.dto.PhoneDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.ScheduleDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Employment
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Schedule
import ru.practicum.android.diploma.domain.models.VacancyDetail

fun VacancyDetailDto.toDomain(): VacancyDetail = VacancyDetail(
    id = id,
    name = name,
    description = description,
    salary = salary?.toDomain(),
    address = address?.toDomain(),
    experience = experience?.toDomain(),
    schedule = schedule?.toDomain(),
    employment = employment?.toDomain(),
    contacts = contacts?.toDomain(),
    employer = employer.toDomain(),
    area = area.toDomain(),
    skills = skills,
    url = url,
    industry = industry.toDomain(),
)

private fun SalaryDto.toDomain(): Salary = Salary(
    from = from,
    to = to,
    currency = currency
)

private fun AddressDto.toDomain(): Address = Address(
    id = id,
    city = city,
    street = street,
    building = building,
    raw = raw
)

private fun ExperienceDto.toDomain(): Experience = Experience(
    id = id,
    name = name
)

private fun ScheduleDto.toDomain(): Schedule = Schedule(
    id = id,
    name = name
)

private fun EmploymentDto.toDomain(): Employment = Employment(
    id = id,
    name = name
)

private fun ContactsDto.toDomain(): Contacts = Contacts(
    id = id,
    name = name,
    email = email,
    phones = phones.map { it.toDomain() }
)

private fun PhoneDto.toDomain(): Phone = Phone(
    comment = comment,
    formatted = formatted
)

private fun EmployerDto.toDomain(): Employer = Employer(
    id = id,
    name = name,
    logo = logo
)


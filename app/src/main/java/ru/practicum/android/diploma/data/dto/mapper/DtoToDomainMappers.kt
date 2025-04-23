package ru.practicum.android.diploma.data.dto.mapper

import ru.practicum.android.diploma.data.dto.additional.AddressDto
import ru.practicum.android.diploma.data.dto.additional.ContactsDto
import ru.practicum.android.diploma.data.dto.additional.EmploymentDto
import ru.practicum.android.diploma.data.dto.additional.ExperienceDto
import ru.practicum.android.diploma.data.dto.additional.KeySkillDto
import ru.practicum.android.diploma.data.dto.additional.MetroStationDto
import ru.practicum.android.diploma.data.dto.additional.PhoneDto
import ru.practicum.android.diploma.data.dto.additional.ProfessionalRoleDto
import ru.practicum.android.diploma.data.dto.additional.ScheduleDto
import ru.practicum.android.diploma.data.dto.main.AreaDto
import ru.practicum.android.diploma.data.dto.main.EmployerDto
import ru.practicum.android.diploma.data.dto.main.IndustryDto
import ru.practicum.android.diploma.data.dto.main.LogoUrlsDto
import ru.practicum.android.diploma.data.dto.main.SalaryDto
import ru.practicum.android.diploma.data.dto.main.VacancyLongDto
import ru.practicum.android.diploma.data.dto.main.VacancyShortDto
import ru.practicum.android.diploma.domain.models.additional.Address
import ru.practicum.android.diploma.domain.models.additional.Contacts
import ru.practicum.android.diploma.domain.models.additional.Employment
import ru.practicum.android.diploma.domain.models.additional.Experience
import ru.practicum.android.diploma.domain.models.additional.KeySkill
import ru.practicum.android.diploma.domain.models.additional.MetroStation
import ru.practicum.android.diploma.domain.models.additional.Phone
import ru.practicum.android.diploma.domain.models.additional.ProfessionalRole
import ru.practicum.android.diploma.domain.models.additional.Schedule
import ru.practicum.android.diploma.domain.models.main.Area
import ru.practicum.android.diploma.domain.models.main.Employer
import ru.practicum.android.diploma.domain.models.main.Industry
import ru.practicum.android.diploma.domain.models.main.LogoUrls
import ru.practicum.android.diploma.domain.models.main.Salary
import ru.practicum.android.diploma.domain.models.main.VacancyLong
import ru.practicum.android.diploma.domain.models.main.VacancyShort

fun VacancyShortDto.toDomain(): VacancyShort = VacancyShort(
    postedAt = this.postedAt.orEmpty(),
    vacancyId = this.vacancyId.orEmpty(),
    logoUrl = this.employer?.logoUrls?.toDomain(),
    name = this.name.orEmpty(),
    area = this.area?.name.orEmpty(),
    employer = this.employer?.name.orEmpty(),
    salary = this.salary?.toDomain(),
    schedule = this.schedule?.toDomain(),
    industries = this.industries.map { it.toDomain() }
)

fun VacancyLongDto.toDomain(): VacancyLong = VacancyLong(
    vacancyId = this.vacancyId,
    name = this.name.orEmpty(),
    description = this.description.orEmpty(),
    salary = this.salary?.toDomain(),
    keySkills = this.keySkills.map { it.toDomain() },
    areaName = this.area?.name.orEmpty(),
    logoUrl = this.employer?.logoUrls?.toDomain(),
    employer = this.employer?.toDomain(),
    experience = this.experience?.toDomain(),
    employmentForm = this.employment?.toDomain(),
    schedule = this.schedule?.toDomain(),
    contacts = this.contacts?.toDomain(),
    address = this.address?.toDomain(),
    industries = this.industries.map { it.toDomain() },
    publishedAt = this.publishedAt.orEmpty(),
    createdAt = this.createdAt.orEmpty(),
    archived = this.archived,
    url = this.url
)

fun AddressDto.toDomain(): Address = Address(
    building = this.building.orEmpty(),
    city = this.city.orEmpty(),
    description = this.description.orEmpty(),
    metroStations = this.metroStations?.map { it.toDomain() } ?: emptyList(),
    street = this.street.orEmpty()
)

fun ContactsDto.toDomain(): Contacts = Contacts(
    name = this.name.orEmpty(),
    email = this.email.orEmpty(),
    phones = this.phones?.map { it.toDomain() } ?: emptyList()
)

fun EmploymentDto.toDomain(): Employment? =
    this.id?.let { Employment.fromIdOrNull(it) }

fun ExperienceDto.toDomain(): Experience? =
    this.id?.let { Experience.fromIdOrNull(it) }

fun KeySkillDto.toDomain(): KeySkill = KeySkill(
    name = this.name.orEmpty()
)

fun MetroStationDto.toDomain(): MetroStation = MetroStation(
    stationId = this.stationId.orEmpty(),
    stationName = this.stationName.orEmpty()
)

fun PhoneDto.toDomain(): Phone = Phone(
    number = this.number.orEmpty(),
    city = this.city.orEmpty(),
    country = this.country.orEmpty()
)

fun ProfessionalRoleDto.toDomain(): ProfessionalRole = ProfessionalRole(
    id = this.id.orEmpty(),
    name = this.name.orEmpty()
)

fun ScheduleDto?.toDomain(): Schedule =
    Schedule.fromId(this?.id) ?: Schedule.FullDay

fun AreaDto.toDomain(): Area = Area(
    id = this.id.orEmpty(),
    name = this.name.orEmpty(),
    url = this.url.orEmpty()
)

fun EmployerDto.toDomain(): Employer = Employer(
    id = this.id.orEmpty(),
    name = this.name.orEmpty(),
    logoUrls = this.logoUrls?.toDomain(),
    url = this.url.orEmpty(),
    trusted = this.trusted ?: false
)

fun IndustryDto.toDomain(): Industry = Industry(
    id = this.id.orEmpty(),
    name = this.name.orEmpty()
)

fun LogoUrlsDto.toDomain(): LogoUrls = LogoUrls(
    logo90 = this.logo90.orEmpty(),
    logo240 = this.logo240.orEmpty(),
    original = this.original.orEmpty()
)

fun SalaryDto.toDomain(): Salary = Salary(
    from = this.from,
    to = this.to,
    currency = this.currency,
    gross = this.gross ?: true
)

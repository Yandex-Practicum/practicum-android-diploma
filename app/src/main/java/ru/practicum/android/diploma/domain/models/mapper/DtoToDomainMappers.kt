package ru.practicum.android.diploma.domain.models.mapper

import android.content.Context
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
import ru.practicum.android.diploma.util.extensions.toFormattedString

fun VacancyShortDto.toDomain(context: Context): VacancyShort = VacancyShort(
    postedAt = this.postedAt,
    vacancyId = this.vacancyId,
    logoUrl = this.employer.logoUrls?.toDomain(),
    name = this.name,
    area = this.area.name,
    employer = this.employer.name,
    salary = this.salary?.toDomain().toFormattedString(context),
    schedule = this.schedule?.toDomain(),
    industries = this.industries.map { it.toDomain() }
)

fun VacancyLongDto.toDomain(context: Context): VacancyLong = VacancyLong(
    vacancyId = this.vacancyId,
    name = this.name,
    description = this.description,
    salary = this.salary?.toDomain().toFormattedString(context),
    keySkills = this.keySkills.map { it.toDomain() },
    areaName = this.area.name,
    logoUrl = this.employer.logoUrls?.toDomain(),
    employer = this.employer.toDomain(),
    experience = this.experience.toDomain(),
    employmentForm = this.employment.toDomain(),
    schedule = this.schedule.toDomain(),
    contacts = this.contacts?.toDomain(),
    address = this.address?.toDomain(),
    industries = this.industries.map { it.toDomain() },
    publishedAt = this.publishedAt,
    createdAt = this.createdAt,
    archived = this.archived
)

fun AddressDto.toDomain(): Address = Address(
    building = this.building,
    city = this.city,
    description = this.description,
    latitude = this.latitude,
    longitude = this.longitude,
    metroStations = this.metroStations?.map { it.toDomain() },
    street = this.street
)

fun ContactsDto.toDomain(): Contacts = Contacts(
    name = this.name,
    email = this.email,
    phones = this.phones.map { it.toDomain() }
)

fun EmploymentDto.toDomain(): Employment =
    Employment.fromId(this.id)

fun ExperienceDto.toDomain(): Experience =
    Experience.fromId(this.id)

fun KeySkillDto.toDomain(): KeySkill = KeySkill(
    name = this.name
)

fun MetroStationDto.toDomain(): MetroStation = MetroStation(
    stationId = this.stationId,
    stationName = this.stationName
)

fun PhoneDto.toDomain(): Phone = Phone(
    number = this.number,
    city = this.city,
    country = this.country
)

fun ProfessionalRoleDto.toDomain(): ProfessionalRole = ProfessionalRole(
    id = this.id,
    name = this.name
)

fun ScheduleDto.toDomain(): Schedule =
    Schedule.fromId(this.id)

fun AreaDto.toDomain(): Area = Area(
    id = this.id,
    name = this.name,
    url = this.url
)

fun EmployerDto.toDomain(): Employer = Employer(
    id = this.id,
    name = this.name,
    logoUrls = this.logoUrls?.toDomain(),
    url = this.url,
    trusted = this.trusted
)

fun IndustryDto.toDomain(): Industry = Industry(
    id = this.id,
    name = this.name
)

fun LogoUrlsDto.toDomain(): LogoUrls = LogoUrls(
    logo90 = this.logo90,
    logo240 = this.logo240,
    original = this.original
)

fun SalaryDto.toDomain(): Salary = Salary(
    from = this.from,
    to = this.to,
    currency = this.currency,
    gross = this.gross
)

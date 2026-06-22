package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AddressDto
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.EmploymentDto
import ru.practicum.android.diploma.data.dto.ExperienceDto
import ru.practicum.android.diploma.data.dto.KeySkillDto
import ru.practicum.android.diploma.data.dto.LogoUrlsDto
import ru.practicum.android.diploma.data.dto.PhoneDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.ScheduleDto
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.dto.VacancyCardDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyCard

fun VacanciesResponse.toModel(): VacanciesSearchResult = VacanciesSearchResult(
    vacancies = this.items.map { it.toModel() },
    vacanciesFound = this.found,
    pagesCount = this.pages,
    currentPage = this.page
)

fun VacancyCardDto.toModel(): VacancyCard = VacancyCard(
    vacancyId = this.vacancyId,
    vacancyName = this.vacancyName,
    companyName = this.companyName,
    areaName = this.areaName,
    salaryFrom = this.salary?.from,
    salaryTo = this.salary?.to,
    currency = this.salary?.currency,
    shareUrl = this.shareUrl
)

fun VacancyCard.toDto(): VacancyCardDto = VacancyCardDto(
    vacancyId = this.vacancyId,
    vacancyName = this.vacancyName,
    companyName = this.companyName,
    areaName = this.areaName,
    salary = SalaryDto(
        from = this.salaryFrom,
        to = this.salaryTo,
        currency = this.currency
    ),
    shareUrl = this.shareUrl
)

fun VacancyDto.toModel(): Vacancy = Vacancy(
    vacancyId = this.id,
    vacancyName = this.name,
    employerId = this.employer?.id,
    companyName = this.employer?.name,
    areaId = this.area?.id,
    areaName = this.area?.name,
    salaryFrom = this.salary?.from,
    salaryTo = this.salary?.to,
    currency = this.salary?.currency,
    logoUrl = this.employer?.logoUrls?.original,
    description = this.description,
    experienceName = this.experience?.name,
    scheduleName = this.schedule?.name,
    employmentName = this.employment?.name,
    addressRaw = this.address?.formatted,
    skills = this.keySkills?.mapNotNull { it.name },
    contactName = this.contacts?.name,
    contactEmail = this.contacts?.email,
    phoneFormatted = this.contacts?.phones?.firstOrNull()?.formatted,
    phoneComment = this.contacts?.phones?.firstOrNull()?.comment,
    shareUrl = this.alternateUrl
)

fun Vacancy.toDto(): VacancyDto = VacancyDto(
    id = this.vacancyId,
    name = this.vacancyName,
    salary = SalaryDto(
        from = this.salaryFrom,
        to = this.salaryTo,
        currency = this.currency
    ),
    employer = EmployerDto(
        id = this.employerId,
        name = this.companyName,
        logoUrls = LogoUrlsDto(
            original = this.logoUrl
        )
    ),
    area = AreaDto(
        id = this.areaId,
        name = this.areaName
    ),
    description = this.description,
    keySkills = this.skills?.map { KeySkillDto(it) },
    experience = ExperienceDto(this.experienceName),
    schedule = ScheduleDto(this.scheduleName),
    employment = EmploymentDto(this.employmentName),
    address = AddressDto(this.addressRaw),
    contacts = ContactsDto(
        name = this.contactName,
        email = this.contactEmail,
        phones = if (this.phoneFormatted != null) {
            listOf(PhoneDto(this.phoneFormatted, this.phoneComment))
        } else {
            null
        }
    ),
    alternateUrl = this.shareUrl
)

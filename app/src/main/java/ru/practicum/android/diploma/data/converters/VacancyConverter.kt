package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.entity.AreaEntity
import ru.practicum.android.diploma.data.db.entity.ContactsEntity
import ru.practicum.android.diploma.data.db.entity.EmployerEntity
import ru.practicum.android.diploma.data.db.entity.SalaryEntity
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.data.dto.AddressDto
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.EmploymentDto
import ru.practicum.android.diploma.data.dto.ExperienceDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.KeySkillDto
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
    logoUrl = this.logoUrl
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
    logoUrl = this.logoUrl
)

fun VacancyDto.toModel(): Vacancy = Vacancy(
    vacancyId = this.id,
    vacancyName = this.name,
    employerId = this.employer?.id,
    companyName = this.employer?.name,
    addressId = this.address?.id,
    addressCity = this.address?.city,
    addressStreet = this.address?.street,
    addressBuilding = this.address?.building,
    areaId = this.area?.id,
    areaName = this.area?.name,
    salaryFrom = this.salary?.from,
    salaryTo = this.salary?.to,
    currency = this.salary?.currency,
    logoUrl = this.employer?.logoUrl,
    description = this.description,
    experienceId = this.experience?.id,
    experienceName = this.experience?.name,
    scheduleId = this.schedule?.id,
    scheduleName = this.schedule?.name,
    employmentName = this.employment?.name,
    addressRaw = this.address?.raw,
    employmentId = this.employment?.id,
    skills = this.keySkills?.mapNotNull { it.name },
    contactId = this.contacts?.id,
    contactName = this.contacts?.name,
    contactEmail = this.contacts?.email,
    phoneFormatted = this.contacts?.phones?.firstOrNull()?.formatted,
    phoneComment = this.contacts?.phones?.firstOrNull()?.comment,
    shareUrl = this.alternateUrl,
    industryId = this.industry.id,
    industryName = this.industry.name
)

fun Vacancy.toDto(): VacancyDto = VacancyDto(
    id = this.vacancyId,
    name = this.vacancyName,
    salary = SalaryDto(from = this.salaryFrom, to = this.salaryTo, currency = this.currency),
    employer = EmployerDto(id = this.employerId, name = this.companyName, logoUrl = this.logoUrl),
    area = AreaDto(id = this.areaId, name = this.areaName),
    description = this.description,
    keySkills = this.skills?.map { KeySkillDto(it) },
    experience = ExperienceDto(id = this.experienceId, name = this.experienceName),
    schedule = ScheduleDto(id = this.scheduleId, name = this.scheduleName),
    employment = EmploymentDto(id = this.employmentId, name = this.companyName),
    address = AddressDto(
        id = this.addressId,
        city = this.addressCity,
        street = this.addressRaw,
        building = this.addressBuilding,
        raw = this.addressRaw
    ),
    contacts = mapToContactsDto(this),
    alternateUrl = this.shareUrl,
    industry = FilterIndustryDto(id = this.industryId, name = this.industryName)
)

private fun mapToContactsDto(vacancy: Vacancy): ContactsDto = ContactsDto(
    id = vacancy.contactId,
    name = vacancy.contactName,
    email = vacancy.contactEmail,
    phones = if (vacancy.phoneFormatted != null) {
        listOf(PhoneDto(vacancy.phoneFormatted, vacancy.phoneComment))
    } else {
        null
    }
)

fun Vacancy.toVacancyCard(): VacancyCard = VacancyCard(
    vacancyId = this.vacancyId,
    vacancyName = this.vacancyName,
    companyName = this.companyName,
    areaName = this.areaName,
    salaryFrom = this.salaryFrom,
    salaryTo = this.salaryTo,
    currency = this.currency,
    logoUrl = this.logoUrl
)

fun Vacancy.toDatabaseEntity(): VacancyEntity = VacancyEntity(
    vacancyId = this.vacancyId,
    vacancyName = this.vacancyName,
    vacancyEmployer = EmployerEntity(
        companyId = this.employerId,
        companyName = this.companyName,
        logoUrl = this.logoUrl
    ),
    vacancyArea = AreaEntity(areaId = this.areaId, areaName = this.areaName),
    vacancySalary = SalaryEntity(
        salaryFrom = this.salaryFrom,
        salaryTo = this.salaryTo,
        currency = this.currency
    ),
    vacancyDescription = this.description,
    vacancyExperienceId = this.experienceId,
    vacancyExperienceName = this.experienceName,
    vacancyScheduleId = this.scheduleId,
    vacancyScheduleName = this.scheduleName,
    vacancyEmploymentId = this.employmentId,
    vacancyEmploymentName = this.employmentName,
    vacancyAddressId = this.addressId,
    vacancyAddressCity = this.addressCity,
    vacancyAddressStreet = this.addressStreet,
    vacancyAddressBuilding = this.addressBuilding,
    vacancyAddressRaw = this.addressRaw,
    vacancySkills = this.skills,
    vacancyContacts = mapToContactsEntity(this),
    vacancyShareUrl = this.shareUrl,
    vacancyIndustryId = this.industryId,
    vacancyIndustryName = this.industryName
)

private fun mapToContactsEntity(vacancy: Vacancy): ContactsEntity = ContactsEntity(
    contactId = vacancy.contactId,
    contactName = vacancy.contactName,
    contactEmail = vacancy.contactEmail,
    contactPhoneFormatted = vacancy.phoneFormatted,
    contactPhoneComment = vacancy.phoneComment
)

fun VacancyEntity.toModel(): Vacancy = Vacancy(
    vacancyId = this.vacancyId,
    vacancyName = this.vacancyName,
    employerId = this.vacancyEmployer?.companyId,
    companyName = this.vacancyEmployer?.companyName,
    areaId = this.vacancyArea?.areaId,
    areaName = this.vacancyArea?.areaName,
    salaryFrom = this.vacancySalary?.salaryFrom,
    salaryTo = this.vacancySalary?.salaryTo,
    currency = this.vacancySalary?.currency,
    logoUrl = this.vacancyEmployer?.logoUrl,
    description = this.vacancyDescription,
    experienceId = this.vacancyExperienceId,
    experienceName = this.vacancyExperienceName,
    scheduleId = this.vacancyScheduleId,
    scheduleName = this.vacancyScheduleName,
    employmentName = this.vacancyEmploymentName,
    employmentId = this.vacancyEmploymentId,
    addressId = this.vacancyAddressId,
    addressCity = this.vacancyAddressCity,
    addressStreet = this.vacancyAddressStreet,
    addressBuilding = this.vacancyAddressBuilding,
    addressRaw = this.vacancyAddressRaw,
    skills = this.vacancySkills,
    contactId = this.vacancyContacts?.contactId,
    contactName = this.vacancyContacts?.contactName,
    contactEmail = this.vacancyContacts?.contactEmail,
    phoneFormatted = this.vacancyContacts?.contactPhoneFormatted,
    phoneComment = this.vacancyContacts?.contactPhoneComment,
    shareUrl = this.vacancyShareUrl,
    industryId = this.vacancyIndustryId,
    industryName = this.vacancyIndustryName
)
